/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.t_teacher.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.t_teacher.entity.T_Teacher;
import com.jeeplus.modules.t_teacher.service.T_TeacherService;

/**
 * 教师Controller
 * @author xy
 * @version 2019-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/t_teacher/t_Teacher")
public class T_TeacherController extends BaseController {

	@Autowired
	private T_TeacherService t_TeacherService;
	
	@ModelAttribute
	public T_Teacher get(@RequestParam(required=false) String id) {
		T_Teacher entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = t_TeacherService.get(id);
		}
		if (entity == null){
			entity = new T_Teacher();
		}
		return entity;
	}
	
	/**
	 * 教师列表页面
	 */
	@RequiresPermissions("t_teacher:t_Teacher:list")
	@RequestMapping(value = {"list", ""})
	public String list(T_Teacher t_Teacher, Model model) {
		model.addAttribute("t_Teacher", t_Teacher);
		return "modules/t_teacher/t_TeacherList";
	}
	
		/**
	 * 教师列表数据
	 */
	@ResponseBody
	@RequiresPermissions("t_teacher:t_Teacher:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(T_Teacher t_Teacher, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<T_Teacher> page = t_TeacherService.findPage(new Page<T_Teacher>(request, response), t_Teacher); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑教师表单页面
	 */
	@RequiresPermissions(value={"t_teacher:t_Teacher:view","t_teacher:t_Teacher:add","t_teacher:t_Teacher:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, T_Teacher t_Teacher, Model model) {
		model.addAttribute("t_Teacher", t_Teacher);
		model.addAttribute("mode", mode);
		return "modules/t_teacher/t_TeacherForm";
	}

	/**
	 * 保存教师
	 */
	@ResponseBody
	@RequiresPermissions(value={"t_teacher:t_Teacher:add","t_teacher:t_Teacher:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(T_Teacher t_Teacher, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(t_Teacher);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		t_TeacherService.save(t_Teacher);//保存
		j.setSuccess(true);
		j.setMsg("保存教师成功");
		return j;
	}
	
	/**
	 * 删除教师
	 */
	@ResponseBody
	@RequiresPermissions("t_teacher:t_Teacher:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(T_Teacher t_Teacher) {
		AjaxJson j = new AjaxJson();
		t_TeacherService.delete(t_Teacher);
		j.setMsg("删除教师成功");
		return j;
	}
	
	/**
	 * 批量删除教师
	 */
	@ResponseBody
	@RequiresPermissions("t_teacher:t_Teacher:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			t_TeacherService.delete(t_TeacherService.get(id));
		}
		j.setMsg("删除教师成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("t_teacher:t_Teacher:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(T_Teacher t_Teacher, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "教师"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<T_Teacher> page = t_TeacherService.findPage(new Page<T_Teacher>(request, response, -1), t_Teacher);
    		new ExportExcel("教师", T_Teacher.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出教师记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("t_teacher:t_Teacher:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<T_Teacher> list = ei.getDataList(T_Teacher.class);
			for (T_Teacher t_Teacher : list){
				try{
					t_TeacherService.save(t_Teacher);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条教师记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条教师记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入教师失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入教师数据模板
	 */
	@ResponseBody
	@RequiresPermissions("t_teacher:t_Teacher:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "教师数据导入模板.xlsx";
    		List<T_Teacher> list = Lists.newArrayList(); 
    		new ExportExcel("教师数据", T_Teacher.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
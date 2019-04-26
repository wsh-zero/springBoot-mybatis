/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.studentteacher.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.jeeplus.modules.studentteacher.entity.StudentTeacher;
import com.jeeplus.modules.studentteacher.service.StudentTeacherService;

/**
 * 学生老师Controller
 * @author xy
 * @version 2019-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/studentteacher/studentTeacher")
public class StudentTeacherController extends BaseController {

	@Autowired
	private StudentTeacherService studentTeacherService;
	
	@ModelAttribute
	public StudentTeacher get(@RequestParam(required=false) String id) {
		StudentTeacher entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentTeacherService.get(id);
		}
		if (entity == null){
			entity = new StudentTeacher();
		}
		return entity;
	}
	
	/**
	 * 学生老师列表页面
	 */
	@RequiresPermissions("studentteacher:studentTeacher:list")
	@RequestMapping(value = {"list", ""})
	public String list(StudentTeacher studentTeacher, Model model) {
		model.addAttribute("studentTeacher", studentTeacher);
		return "modules/studentteacher/studentTeacherList";
	}
	
		/**
	 * 学生老师列表数据
	 */
	@ResponseBody
	@RequiresPermissions("studentteacher:studentTeacher:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StudentTeacher studentTeacher, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentTeacher> page = studentTeacherService.findPage(new Page<StudentTeacher>(request, response), studentTeacher); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑学生老师表单页面
	 */
	@RequiresPermissions(value={"studentteacher:studentTeacher:view","studentteacher:studentTeacher:add","studentteacher:studentTeacher:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, StudentTeacher studentTeacher, Model model) {
		model.addAttribute("studentTeacher", studentTeacher);
		model.addAttribute("mode", mode);
		return "modules/studentteacher/studentTeacherForm";
	}

	/**
	 * 保存学生老师
	 */
	@ResponseBody
	@RequiresPermissions(value={"studentteacher:studentTeacher:add","studentteacher:studentTeacher:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(StudentTeacher studentTeacher, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(studentTeacher);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		studentTeacherService.save(studentTeacher);//保存
		j.setSuccess(true);
		j.setMsg("保存学生老师成功");
		return j;
	}
	
	/**
	 * 删除学生老师
	 */
	@ResponseBody
	@RequiresPermissions("studentteacher:studentTeacher:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StudentTeacher studentTeacher) {
		AjaxJson j = new AjaxJson();
		studentTeacherService.delete(studentTeacher);
		j.setMsg("删除学生老师成功");
		return j;
	}
	
	/**
	 * 批量删除学生老师
	 */
	@ResponseBody
	@RequiresPermissions("studentteacher:studentTeacher:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			studentTeacherService.delete(studentTeacherService.get(id));
		}
		j.setMsg("删除学生老师成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("studentteacher:studentTeacher:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(StudentTeacher studentTeacher, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "学生老师"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StudentTeacher> page = studentTeacherService.findPage(new Page<StudentTeacher>(request, response, -1), studentTeacher);
    		new ExportExcel("学生老师", StudentTeacher.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出学生老师记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("studentteacher:studentTeacher:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StudentTeacher> list = ei.getDataList(StudentTeacher.class);
			for (StudentTeacher studentTeacher : list){
				try{
					studentTeacherService.save(studentTeacher);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条学生老师记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条学生老师记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入学生老师失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入学生老师数据模板
	 */
	@ResponseBody
	@RequiresPermissions("studentteacher:studentTeacher:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "学生老师数据导入模板.xlsx";
    		List<StudentTeacher> list = Lists.newArrayList(); 
    		new ExportExcel("学生老师数据", StudentTeacher.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.empee.web;

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
import com.jeeplus.modules.empee.entity.T_Empee;
import com.jeeplus.modules.empee.service.T_EmpeeService;

/**
 * 员工Controller
 * @author xy
 * @version 2019-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/empee/t_Empee")
public class T_EmpeeController extends BaseController {

	@Autowired
	private T_EmpeeService t_EmpeeService;
	
	@ModelAttribute
	public T_Empee get(@RequestParam(required=false) String id) {
		T_Empee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = t_EmpeeService.get(id);
		}
		if (entity == null){
			entity = new T_Empee();
		}
		return entity;
	}
	
	/**
	 * 员工列表页面
	 */
	@RequiresPermissions("empee:t_Empee:list")
	@RequestMapping(value = {"list", ""})
	public String list(T_Empee t_Empee, Model model) {
		model.addAttribute("t_Empee", t_Empee);
		return "modules/empee/t_EmpeeList";
	}
	
		/**
	 * 员工列表数据
	 */
	@ResponseBody
	@RequiresPermissions("empee:t_Empee:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(T_Empee t_Empee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<T_Empee> page = t_EmpeeService.findPage(new Page<T_Empee>(request, response), t_Empee); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工表单页面
	 */
	@RequiresPermissions(value={"empee:t_Empee:view","empee:t_Empee:add","empee:t_Empee:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(T_Empee t_Empee, Model model) {
		model.addAttribute("t_Empee", t_Empee);
		return "modules/empee/t_EmpeeForm";
	}

	/**
	 * 保存员工
	 */
	@ResponseBody
	@RequiresPermissions(value={"empee:t_Empee:add","empee:t_Empee:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(T_Empee t_Empee, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(t_Empee);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		t_EmpeeService.save(t_Empee);//保存
		j.setSuccess(true);
		j.setMsg("保存员工成功");
		return j;
	}
	
	/**
	 * 删除员工
	 */
	@ResponseBody
	@RequiresPermissions("empee:t_Empee:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(T_Empee t_Empee) {
		AjaxJson j = new AjaxJson();
		t_EmpeeService.delete(t_Empee);
		j.setMsg("删除员工成功");
		return j;
	}
	
	/**
	 * 批量删除员工
	 */
	@ResponseBody
	@RequiresPermissions("empee:t_Empee:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			t_EmpeeService.delete(t_EmpeeService.get(id));
		}
		j.setMsg("删除员工成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("empee:t_Empee:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(T_Empee t_Empee, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<T_Empee> page = t_EmpeeService.findPage(new Page<T_Empee>(request, response, -1), t_Empee);
    		new ExportExcel("员工", T_Empee.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("empee:t_Empee:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<T_Empee> list = ei.getDataList(T_Empee.class);
			for (T_Empee t_Empee : list){
				try{
					t_EmpeeService.save(t_Empee);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工数据模板
	 */
	@ResponseBody
	@RequiresPermissions("empee:t_Empee:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工数据导入模板.xlsx";
    		List<T_Empee> list = Lists.newArrayList(); 
    		new ExportExcel("员工数据", T_Empee.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
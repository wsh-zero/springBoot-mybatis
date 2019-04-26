/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;
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
import com.jeeplus.modules.personnel.plan.entity.Education;
import com.jeeplus.modules.personnel.plan.service.EducationService;

/**
 * 学历管理Controller
 * @author 王伟
 * @version 2019-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/plan/education")
public class EducationController extends BaseController {

	@Autowired
	private EducationService educationService;
	@Autowired
	private StaffService staffService;


	@ModelAttribute
	public Education get(@RequestParam(required=false) String id) {
		Education entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = educationService.get(id);
		}
		if (entity == null){
			entity = new Education();
		}
		return entity;
	}

	/**
	 * 学历列表页面
	 */
	@RequiresPermissions("personnel:plan:education:list")
	@RequestMapping(value = {"list", ""})
	public String list(Education education, Model model) {
		model.addAttribute("education", education);
		return "modules/personnel/plan/educationList";
	}

	/**
	 * 学历列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:education:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Education education, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Education> page = educationService.findPage(new Page<Education>(request, response), education);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑学历表单页面
	 */
	@RequiresPermissions(value={"personnel:plan:education:view","personnel:plan:education:add","personnel:plan:education:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Education education, Model model) {
		model.addAttribute("education", education);
		return "modules/personnel/plan/educationForm";
	}

	/**
	 * 保存学历
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:plan:education:add","personnel:plan:education:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Education education, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(education);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		j = educationService.preserve(education);//保存
		return j;
	}

	/**
	 * 删除学历
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:education:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Education education) {
		AjaxJson j = new AjaxJson();
		educationService.delete(education);
		j.setMsg("删除学历成功");
		return j;
	}

	/**
	 * 批量删除学历
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:education:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			Education education = new Education();
			education.setId(id);
			Staff  staff = new Staff();
			staff.setEducation(education);
			int count = staffService.count(staff);
			if (count > 0){
				Education e = educationService.get(id);
				j.setSuccess(false);
				j.setMsg("当前学历"+e.getEducate()+"使用中，无法删除");
				return j;
			}
			educationService.delete(educationService.get(id));
		}
		j.setMsg("删除学历成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:education:export")
	@RequestMapping(value = "export")
	public AjaxJson exportFile(Education education, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "学历"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Education> page = educationService.findPage(new Page<Education>(request, response, -1), education);
			new ExportExcel("学历", Education.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出学历记录失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:education:import")
	@RequestMapping(value = "import")
	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Education> list = ei.getDataList(Education.class);
			for (Education education : list){
				try{
					educationService.save(education);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条学历记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条学历记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入学历失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 下载导入学历数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:education:import")
	@RequestMapping(value = "import/template")
	public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "学历数据导入模板.xlsx";
			List<Education> list = Lists.newArrayList();
			new ExportExcel("学历数据", Education.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
	}

}
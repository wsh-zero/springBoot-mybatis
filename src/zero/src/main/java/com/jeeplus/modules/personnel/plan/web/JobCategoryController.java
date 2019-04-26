/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.personnel.plan.mapper.StationMapper;
import com.jeeplus.modules.personnel.plan.service.StationService;
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
import com.jeeplus.modules.personnel.plan.entity.JobCategory;
import com.jeeplus.modules.personnel.plan.service.JobCategoryService;

/**
 * 岗位类别Controller
 * @author 王伟
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/plan/jobCategory")
public class JobCategoryController extends BaseController {

	@Autowired
	private JobCategoryService jobCategoryService;

	@Autowired
	private StationMapper stationMapper;
	
	@ModelAttribute
	public JobCategory get(@RequestParam(required=false) String id) {
		JobCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jobCategoryService.get(id);
		}
		if (entity == null){
			entity = new JobCategory();
		}
		return entity;
	}
	
	/**
	 * 岗位类别列表页面
	 */
	@RequiresPermissions("personnel:plan:jobCategory:list")
	@RequestMapping(value = {"list", ""})
	public String list(JobCategory jobCategory, Model model) {
		model.addAttribute("jobCategory", jobCategory);
		return "modules/personnel/plan/jobCategoryList";
	}
	
		/**
	 * 岗位类别列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:jobCategory:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(JobCategory jobCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JobCategory> page = jobCategoryService.findPage(new Page<JobCategory>(request, response), jobCategory); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑岗位类别表单页面
	 */
	@RequiresPermissions(value={"personnel:plan:jobCategory:view","personnel:plan:jobCategory:add","personnel:plan:jobCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(JobCategory jobCategory, Model model) {
		model.addAttribute("jobCategory", jobCategory);
		return "modules/personnel/plan/jobCategoryForm";
	}

	/**
	 * 保存岗位类别
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:plan:jobCategory:add","personnel:plan:jobCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(JobCategory jobCategory, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jobCategory);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		try {
			j = jobCategoryService.preserve(jobCategory);//保存
			return j;
		}catch (Exception e){
			return j;
		}


	}
	
	/**
	 * 删除岗位类别
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:jobCategory:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(JobCategory jobCategory) {
		AjaxJson j = new AjaxJson();
		jobCategoryService.delete(jobCategory);
		j.setMsg("删除岗位类型成功");
		return j;
	}
	
	/**
	 * 批量删除岗位类别
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:jobCategory:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			JobCategory jobCategory = new JobCategory();
			jobCategory.setId(id);
			Station station = new Station();
			station.setJobType(jobCategory);
			int count = stationMapper.count(station);
			if (count>0){
				JobCategory job = jobCategoryService.get(id);
				j.setSuccess(false);
				j.setMsg("当前岗位类型"+job.getJobType()+"使用中，无法删除");
				return j;
			}
			jobCategoryService.delete(jobCategoryService.get(id));
		}
		j.setMsg("删除岗位类型成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:jobCategory:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(JobCategory jobCategory, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "岗位类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<JobCategory> page = jobCategoryService.findPage(new Page<JobCategory>(request, response, -1), jobCategory);
    		new ExportExcel("岗位类型", JobCategory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出岗位类型记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:jobCategory:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<JobCategory> list = ei.getDataList(JobCategory.class);
			for (JobCategory jobCategory : list){
				try{
					jobCategoryService.save(jobCategory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条岗位类别记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条岗位类别记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入岗位类别失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入岗位类别数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:jobCategory:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "岗位类别数据导入模板.xlsx";
    		List<JobCategory> list = Lists.newArrayList(); 
    		new ExportExcel("岗位类别数据", JobCategory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
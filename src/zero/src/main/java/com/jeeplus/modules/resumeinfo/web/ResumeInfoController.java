/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resumeinfo.web;

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
import com.jeeplus.modules.resumeinfo.entity.ResumeInfo;
import com.jeeplus.modules.resumeinfo.service.ResumeInfoService;

/**
 * 简历管理Controller
 * @author chentao
 * @version 2019-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/resumeinfo/resumeInfo")
public class ResumeInfoController extends BaseController {

	@Autowired
	private ResumeInfoService resumeInfoService;
	
	@ModelAttribute
	public ResumeInfo get(@RequestParam(required=false) String id) {
		ResumeInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resumeInfoService.get(id);
		}
		if (entity == null){
			entity = new ResumeInfo();
		}
		return entity;
	}
	
	/**
	 * 简历管理列表页面
	 */
	@RequiresPermissions("resumeinfo:resumeInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list(ResumeInfo resumeInfo, Model model) {
		model.addAttribute("resumeInfo", resumeInfo);
		return "modules/resumeinfo/resumeInfoList";
	}
	
		/**
	 * 简历管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resumeinfo:resumeInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ResumeInfo resumeInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResumeInfo> page = resumeInfoService.findPage(new Page<ResumeInfo>(request, response), resumeInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑简历管理表单页面
	 */
	@RequiresPermissions(value={"resumeinfo:resumeInfo:view","resumeinfo:resumeInfo:add","resumeinfo:resumeInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, ResumeInfo resumeInfo, Model model) {
		model.addAttribute("resumeInfo", resumeInfo);
		model.addAttribute("mode", mode);
		return "modules/resumeinfo/resumeInfoView";
	}

	/**
	 * 保存简历管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"resumeinfo:resumeInfo:add","resumeinfo:resumeInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ResumeInfo resumeInfo, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(resumeInfo);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		resumeInfoService.save(resumeInfo);//保存
		j.setSuccess(true);
		j.setMsg("保存简历管理成功");
		return j;
	}
	
	/**
	 * 删除简历管理
	 */
	@ResponseBody
	@RequiresPermissions("resumeinfo:resumeInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ResumeInfo resumeInfo) {
		AjaxJson j = new AjaxJson();
		resumeInfoService.delete(resumeInfo);
		j.setMsg("删除简历管理成功");
		return j;
	}
	
	/**
	 * 批量删除简历管理
	 */
	@ResponseBody
	@RequiresPermissions("resumeinfo:resumeInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resumeInfoService.delete(resumeInfoService.get(id));
		}
		j.setMsg("删除简历管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("resumeinfo:resumeInfo:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(ResumeInfo resumeInfo, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "简历管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResumeInfo> page = resumeInfoService.findPage(new Page<ResumeInfo>(request, response, -1), resumeInfo);
    		new ExportExcel("简历管理", ResumeInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出简历管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("resumeinfo:resumeInfo:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResumeInfo> list = ei.getDataList(ResumeInfo.class);
			for (ResumeInfo resumeInfo : list){
				try{
					resumeInfoService.save(resumeInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条简历管理记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条简历管理记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入简历管理失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入简历管理数据模板
	 */
	@ResponseBody
	@RequiresPermissions("resumeinfo:resumeInfo:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "简历管理数据导入模板.xlsx";
    		List<ResumeInfo> list = Lists.newArrayList(); 
    		new ExportExcel("简历管理数据", ResumeInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

    @RequestMapping("/resumecontent")
    public String resumeContent(String id, Model model) {

		ResumeInfo info = resumeInfoService.get(id);
		model.addAttribute("resumeInfo", info);
		return "modules/resumeinfo/contentPage";
	}
}
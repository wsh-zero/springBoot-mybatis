/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.achieve.web;

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
import com.jeeplus.modules.personnel.achieve.entity.PerStandard;
import com.jeeplus.modules.personnel.achieve.service.PerStandardService;

/**
 * 评定标准Controller
 * @author ww
 * @version 2019-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/achieve/perStandard")
public class PerStandardController extends BaseController {

	@Autowired
	private PerStandardService perStandardService;
	
	@ModelAttribute
	public PerStandard get(@RequestParam(required=false) String id) {
		PerStandard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = perStandardService.get(id);
		}
		if (entity == null){
			entity = new PerStandard();
		}
		return entity;
	}
	
	/**
	 * 评定标准列表页面
	 */
	@RequiresPermissions("personnel:achieve:perStandard:list")
	@RequestMapping(value = {"list", ""})
	public String list(PerStandard perStandard, Model model) {
		model.addAttribute("perStandard", perStandard);
		return "modules/personnel/achieve/perStandardList";
	}
	
		/**
	 * 评定标准列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:perStandard:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PerStandard perStandard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PerStandard> page = perStandardService.findPage(new Page<PerStandard>(request, response), perStandard);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑评定标准表单页面
	 */
	@RequestMapping(value = "form")
	public String form(PerStandard perStandard, Model model) {
		model.addAttribute("perStandard", perStandard);
		return "modules/personnel/achieve/perStandardForm";
	}

	/**
	 * 保存评定标准
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(PerStandard perStandard, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(perStandard);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		try {
			if (perStandard.getIsNewRecord()) {
				int count = perStandardService.count(perStandard);
				if (count > 0){
					j.setSuccess(false);
					j.setMsg("考核标准重复");
					return j;
				}
			}else {
				PerStandard p = perStandardService.find(perStandard);
				if (p!=null && !p.getId().equals(perStandard.getId())){
					j.setSuccess(false);
					j.setMsg("考核标准重复");
					return j;
				}
			}
		}catch (Exception e){
			e.printStackTrace();

		}
		perStandardService.save(perStandard);//保存
		j.setSuccess(true);
		j.setMsg("保存考核标准成功");
		return j;
	}
	
	/**
	 * 删除评定标准
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(PerStandard perStandard) {
		AjaxJson j = new AjaxJson();
		perStandardService.delete(perStandard);
		j.setMsg("删除评定标准成功");
		return j;
	}
	
	/**
	 * 批量删除评定标准
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			perStandardService.delete(perStandardService.get(id));
		}
		j.setMsg("删除评定标准成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export")
    public AjaxJson exportFile(PerStandard perStandard, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "评定标准"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PerStandard> page = perStandardService.findPage(new Page<PerStandard>(request, response, -1), perStandard);
    		new ExportExcel("评定标准", PerStandard.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出评定标准记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PerStandard> list = ei.getDataList(PerStandard.class);
			for (PerStandard perStandard : list){
				try{
					perStandardService.save(perStandard);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条评定标准记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条评定标准记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入评定标准失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入评定标准数据模板
	 */
	@ResponseBody
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "评定标准数据导入模板.xlsx";
    		List<PerStandard> list = Lists.newArrayList(); 
    		new ExportExcel("评定标准数据", PerStandard.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
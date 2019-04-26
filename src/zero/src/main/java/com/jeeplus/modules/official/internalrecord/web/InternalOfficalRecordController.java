/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.internalrecord.web;

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
import com.jeeplus.modules.official.internalrecord.entity.InternalOfficalRecord;
import com.jeeplus.modules.official.internalrecord.service.InternalOfficalRecordService;

/**
 * 内部公文接收状态Controller
 * @author chentao
 * @version 2019-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/official/internalrecord/internalOfficalRecord")
public class InternalOfficalRecordController extends BaseController {

	@Autowired
	private InternalOfficalRecordService internalOfficalRecordService;
	
	@ModelAttribute
	public InternalOfficalRecord get(@RequestParam(required=false) String id) {
		InternalOfficalRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = internalOfficalRecordService.get(id);
		}
		if (entity == null){
			entity = new InternalOfficalRecord();
		}
		return entity;
	}
	
	/**
	 * x列表页面
	 */
	@RequiresPermissions("official:internalrecord:internalOfficalRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list(InternalOfficalRecord internalOfficalRecord, Model model) {
		model.addAttribute("internalOfficalRecord", internalOfficalRecord);
		return "modules/official/internalrecord/internalOfficalRecordList";
	}
	
		/**
	 * x列表数据
	 */
	@ResponseBody
	@RequiresPermissions("official:internalrecord:internalOfficalRecord:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(InternalOfficalRecord internalOfficalRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InternalOfficalRecord> page = internalOfficalRecordService.findPage(new Page<InternalOfficalRecord>(request, response), internalOfficalRecord); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑x表单页面
	 */
	@RequiresPermissions(value={"official:internalrecord:internalOfficalRecord:view","official:internalrecord:internalOfficalRecord:add","official:internalrecord:internalOfficalRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, InternalOfficalRecord internalOfficalRecord, Model model) {
		model.addAttribute("internalOfficalRecord", internalOfficalRecord);
		model.addAttribute("mode", mode);
		return "modules/official/internalrecord/internalOfficalRecordForm";
	}

	/**
	 * 保存x
	 */
	@ResponseBody
	@RequiresPermissions(value={"official:internalrecord:internalOfficalRecord:add","official:internalrecord:internalOfficalRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(InternalOfficalRecord internalOfficalRecord, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(internalOfficalRecord);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		internalOfficalRecordService.save(internalOfficalRecord);//保存
		j.setSuccess(true);
		j.setMsg("保存x成功");
		return j;
	}
	
	/**
	 * 删除x
	 */
	@ResponseBody
	@RequiresPermissions("official:internalrecord:internalOfficalRecord:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(InternalOfficalRecord internalOfficalRecord) {
		AjaxJson j = new AjaxJson();
		internalOfficalRecordService.delete(internalOfficalRecord);
		j.setMsg("删除x成功");
		return j;
	}
	
	/**
	 * 批量删除x
	 */
	@ResponseBody
	@RequiresPermissions("official:internalrecord:internalOfficalRecord:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			internalOfficalRecordService.delete(internalOfficalRecordService.get(id));
		}
		j.setMsg("删除x成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("official:internalrecord:internalOfficalRecord:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(InternalOfficalRecord internalOfficalRecord, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "x"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<InternalOfficalRecord> page = internalOfficalRecordService.findPage(new Page<InternalOfficalRecord>(request, response, -1), internalOfficalRecord);
    		new ExportExcel("x", InternalOfficalRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出x记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("official:internalrecord:internalOfficalRecord:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<InternalOfficalRecord> list = ei.getDataList(InternalOfficalRecord.class);
			for (InternalOfficalRecord internalOfficalRecord : list){
				try{
					internalOfficalRecordService.save(internalOfficalRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条x记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条x记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入x失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入x数据模板
	 */
	@ResponseBody
	@RequiresPermissions("official:internalrecord:internalOfficalRecord:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "x数据导入模板.xlsx";
    		List<InternalOfficalRecord> list = Lists.newArrayList(); 
    		new ExportExcel("x数据", InternalOfficalRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
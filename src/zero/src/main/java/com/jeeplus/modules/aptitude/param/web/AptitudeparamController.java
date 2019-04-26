/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.aptitude.param.web;

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
import com.jeeplus.modules.aptitude.param.entity.Aptitudeparam;
import com.jeeplus.modules.aptitude.param.service.AptitudeparamService;

/**
 * 资质参数配置Controller
 * @author xy
 * @version 2019-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/aptitude/param/aptitudeparam")
public class AptitudeparamController extends BaseController {

	@Autowired
	private AptitudeparamService aptitudeparamService;
	
	@ModelAttribute
	public Aptitudeparam get(@RequestParam(required=false) String id) {
		Aptitudeparam entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aptitudeparamService.get(id);
		}
		if (entity == null){
			entity = new Aptitudeparam();
		}
		return entity;
	}
	
	/**
	 * 资质参数列表页面
	 */
	@RequiresPermissions("aptitude:param:aptitudeparam:list")
	@RequestMapping(value = {"list", ""})
	public String list(Aptitudeparam aptitudeparam, Model model) {
		model.addAttribute("aptitudeparam", aptitudeparam);
		return "modules/aptitude/param/aptitudeparamList";
	}
	
		/**
	 * 资质参数列表数据
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:param:aptitudeparam:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Aptitudeparam aptitudeparam, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Aptitudeparam> page = aptitudeparamService.findPage(new Page<Aptitudeparam>(request, response), aptitudeparam); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资质参数表单页面
	 */
	@RequiresPermissions(value={"aptitude:param:aptitudeparam:view","aptitude:param:aptitudeparam:add","aptitude:param:aptitudeparam:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Aptitudeparam aptitudeparam, Model model) {
		model.addAttribute("aptitudeparam", aptitudeparam);
		model.addAttribute("mode", mode);
		return "modules/aptitude/param/aptitudeparamForm";
	}

	/**
	 * 保存资质参数
	 */
	@ResponseBody
	@RequiresPermissions(value={"aptitude:param:aptitudeparam:add","aptitude:param:aptitudeparam:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Aptitudeparam aptitudeparam, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(aptitudeparam);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		aptitudeparamService.save(aptitudeparam);//保存
		j.setSuccess(true);
		j.setMsg("保存资质参数成功");
		return j;
	}
	
	/**
	 * 删除资质参数
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:param:aptitudeparam:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Aptitudeparam aptitudeparam) {
		AjaxJson j = new AjaxJson();
		aptitudeparamService.delete(aptitudeparam);
		j.setMsg("删除资质参数成功");
		return j;
	}
	
	/**
	 * 批量删除资质参数
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:param:aptitudeparam:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			aptitudeparamService.delete(aptitudeparamService.get(id));
		}
		j.setMsg("删除资质参数成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:param:aptitudeparam:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Aptitudeparam aptitudeparam, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资质参数"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Aptitudeparam> page = aptitudeparamService.findPage(new Page<Aptitudeparam>(request, response, -1), aptitudeparam);
    		new ExportExcel("资质参数", Aptitudeparam.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资质参数记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("aptitude:param:aptitudeparam:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Aptitudeparam> list = ei.getDataList(Aptitudeparam.class);
			for (Aptitudeparam aptitudeparam : list){
				try{
					aptitudeparamService.save(aptitudeparam);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资质参数记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条资质参数记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入资质参数失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入资质参数数据模板
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:param:aptitudeparam:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资质参数数据导入模板.xlsx";
    		List<Aptitudeparam> list = Lists.newArrayList(); 
    		new ExportExcel("资质参数数据", Aptitudeparam.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
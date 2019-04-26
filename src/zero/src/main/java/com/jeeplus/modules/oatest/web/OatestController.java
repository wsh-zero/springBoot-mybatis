/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oatest.web;

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
import com.jeeplus.modules.oatest.entity.Oatest;
import com.jeeplus.modules.oatest.service.OatestService;

/**
 * 随意测试Controller
 * @author xy
 * @version 2019-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/oatest/oatest")
public class OatestController extends BaseController {

	@Autowired
	private OatestService oatestService;
	
	@ModelAttribute
	public Oatest get(@RequestParam(required=false) String id) {
		Oatest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oatestService.get(id);
		}
		if (entity == null){
			entity = new Oatest();
		}
		return entity;
	}
	
	/**
	 * 随意测试列表页面
	 */
	@RequiresPermissions("oatest:oatest:list")
	@RequestMapping(value = {"list", ""})
	public String list(Oatest oatest, Model model) {
		model.addAttribute("oatest", oatest);
		return "modules/oatest/oatestList";
	}
	
		/**
	 * 随意测试列表数据
	 */
	@ResponseBody
	@RequiresPermissions("oatest:oatest:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Oatest oatest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Oatest> page = oatestService.findPage(new Page<Oatest>(request, response), oatest); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑随意测试表单页面
	 */
	@RequiresPermissions(value={"oatest:oatest:view","oatest:oatest:add","oatest:oatest:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Oatest oatest, Model model) {
		model.addAttribute("oatest", oatest);
		model.addAttribute("mode", mode);
		return "modules/oatest/oatestForm";
	}

	/**
	 * 保存随意测试
	 */
	@ResponseBody
	@RequiresPermissions(value={"oatest:oatest:add","oatest:oatest:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Oatest oatest, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(oatest);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		oatestService.save(oatest);//保存
		j.setSuccess(true);
		j.setMsg("保存随意测试成功");
		return j;
	}
	
	/**
	 * 删除随意测试
	 */
	@ResponseBody
	@RequiresPermissions("oatest:oatest:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Oatest oatest) {
		AjaxJson j = new AjaxJson();
		oatestService.delete(oatest);
		j.setMsg("删除随意测试成功");
		return j;
	}
	
	/**
	 * 批量删除随意测试
	 */
	@ResponseBody
	@RequiresPermissions("oatest:oatest:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oatestService.delete(oatestService.get(id));
		}
		j.setMsg("删除随意测试成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("oatest:oatest:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Oatest oatest, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "随意测试"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Oatest> page = oatestService.findPage(new Page<Oatest>(request, response, -1), oatest);
    		new ExportExcel("随意测试", Oatest.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出随意测试记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("oatest:oatest:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Oatest> list = ei.getDataList(Oatest.class);
			for (Oatest oatest : list){
				try{
					oatestService.save(oatest);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条随意测试记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条随意测试记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入随意测试失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入随意测试数据模板
	 */
	@ResponseBody
	@RequiresPermissions("oatest:oatest:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "随意测试数据导入模板.xlsx";
    		List<Oatest> list = Lists.newArrayList(); 
    		new ExportExcel("随意测试数据", Oatest.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
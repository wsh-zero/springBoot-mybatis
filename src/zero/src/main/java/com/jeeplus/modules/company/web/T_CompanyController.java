/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.company.web;

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
import com.jeeplus.modules.company.entity.T_Company;
import com.jeeplus.modules.company.service.T_CompanyService;

/**
 * 公司Controller
 * @author xy
 * @version 2019-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/company/t_Company")
public class T_CompanyController extends BaseController {

	@Autowired
	private T_CompanyService t_CompanyService;
	
	@ModelAttribute
	public T_Company get(@RequestParam(required=false) String id) {
		T_Company entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = t_CompanyService.get(id);
		}
		if (entity == null){
			entity = new T_Company();
		}
		return entity;
	}
	
	/**
	 * 公司列表页面
	 */
	@RequiresPermissions("company:t_Company:list")
	@RequestMapping(value = {"list", ""})
	public String list(T_Company t_Company, Model model) {
		model.addAttribute("t_Company", t_Company);
		return "modules/company/t_CompanyList";
	}
	
		/**
	 * 公司列表数据
	 */
	@ResponseBody
	@RequiresPermissions("company:t_Company:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(T_Company t_Company, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<T_Company> page = t_CompanyService.findPage(new Page<T_Company>(request, response), t_Company); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑公司表单页面
	 */
	@RequiresPermissions(value={"company:t_Company:view","company:t_Company:add","company:t_Company:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, T_Company t_Company, Model model) {
		model.addAttribute("t_Company", t_Company);
		model.addAttribute("mode", mode);
		return "modules/company/t_CompanyForm";
	}

	/**
	 * 保存公司
	 */
	@ResponseBody
	@RequiresPermissions(value={"company:t_Company:add","company:t_Company:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(T_Company t_Company, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(t_Company);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		t_CompanyService.save(t_Company);//保存
		j.setSuccess(true);
		j.setMsg("保存公司成功");
		return j;
	}
	
	/**
	 * 删除公司
	 */
	@ResponseBody
	@RequiresPermissions("company:t_Company:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(T_Company t_Company) {
		AjaxJson j = new AjaxJson();
		t_CompanyService.delete(t_Company);
		j.setMsg("删除公司成功");
		return j;
	}
	
	/**
	 * 批量删除公司
	 */
	@ResponseBody
	@RequiresPermissions("company:t_Company:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			t_CompanyService.delete(t_CompanyService.get(id));
		}
		j.setMsg("删除公司成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("company:t_Company:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(T_Company t_Company, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "公司"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<T_Company> page = t_CompanyService.findPage(new Page<T_Company>(request, response, -1), t_Company);
    		new ExportExcel("公司", T_Company.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出公司记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("company:t_Company:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<T_Company> list = ei.getDataList(T_Company.class);
			for (T_Company t_Company : list){
				try{
					t_CompanyService.save(t_Company);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条公司记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条公司记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入公司失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入公司数据模板
	 */
	@ResponseBody
	@RequiresPermissions("company:t_Company:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "公司数据导入模板.xlsx";
    		List<T_Company> list = Lists.newArrayList(); 
    		new ExportExcel("公司数据", T_Company.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
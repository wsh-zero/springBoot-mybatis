/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.aptitude.company.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.number.NumberUtil;
import com.jeeplus.modules.aptitude.util.NumberUtils;
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
import com.jeeplus.modules.aptitude.company.entity.Companyaptitude;
import com.jeeplus.modules.aptitude.company.service.CompanyaptitudeService;

/**
 * 公司资质管理Controller
 * @author xy
 * @version 2019-03-04
 */
@Controller
@RequestMapping(value = "${adminPath}/aptitude/company/companyaptitude")
public class CompanyaptitudeController extends BaseController {

	@Autowired
	private CompanyaptitudeService companyaptitudeService;
	
	@ModelAttribute
	public Companyaptitude get(@RequestParam(required=false) String id) {
		Companyaptitude entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyaptitudeService.get(id);
		}
		if (entity == null){
			entity = new Companyaptitude();
		}
		return entity;
	}
	
	/**
	 * 公司资质列表页面
	 */
	@RequiresPermissions("aptitude:company:companyaptitude:list")
	@RequestMapping(value = {"list", ""})
	public String list(Companyaptitude companyaptitude, Model model) {
		model.addAttribute("companyaptitude", companyaptitude);
		return "modules/aptitude/company/companyaptitudeList";
	}
	
		/**
	 * 公司资质列表数据
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:company:companyaptitude:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Companyaptitude companyaptitude, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Companyaptitude> page = companyaptitudeService.findPage(new Page<Companyaptitude>(request, response), companyaptitude); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑公司资质表单页面
	 */
	@RequiresPermissions(value={"aptitude:company:companyaptitude:view","aptitude:company:companyaptitude:add","aptitude:company:companyaptitude:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Companyaptitude companyaptitude, Model model) {
		model.addAttribute("companyaptitude", companyaptitude);
		model.addAttribute("mode", mode);
		return "modules/aptitude/company/companyaptitudeForm";
	}

	/**
	 * 保存公司资质
	 */
	@ResponseBody
	@RequiresPermissions(value={"aptitude:company:companyaptitude:add","aptitude:company:companyaptitude:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Companyaptitude companyaptitude, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		boolean flag=NumberUtils.isPureDigital(companyaptitude.getValidtime()+"");
		if(flag==false){
			j.setSuccess(false);
			j.setMsg("有效期限只能为整数");
			return j;
		}
		String errMsg = beanValidator(companyaptitude);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		companyaptitudeService.save(companyaptitude);//保存
		j.setSuccess(true);
		j.setMsg("保存公司资质成功");
		return j;
	}
	
	/**
	 * 删除公司资质
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:company:companyaptitude:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Companyaptitude companyaptitude) {
		AjaxJson j = new AjaxJson();
		companyaptitudeService.delete(companyaptitude);
		j.setMsg("删除公司资质成功");
		return j;
	}
	
	/**
	 * 批量删除公司资质
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:company:companyaptitude:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			companyaptitudeService.delete(companyaptitudeService.get(id));
		}
		j.setMsg("删除公司资质成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:company:companyaptitude:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Companyaptitude companyaptitude, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "公司资质"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Companyaptitude> page = companyaptitudeService.findPage(new Page<Companyaptitude>(request, response, -1), companyaptitude);
    		new ExportExcel("公司资质", Companyaptitude.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出公司资质记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("aptitude:company:companyaptitude:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Companyaptitude> list = ei.getDataList(Companyaptitude.class);
			for (Companyaptitude companyaptitude : list){
				try{
					companyaptitudeService.save(companyaptitude);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条公司资质记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条公司资质记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入公司资质失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入公司资质数据模板
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:company:companyaptitude:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "公司资质数据导入模板.xlsx";
    		List<Companyaptitude> list = Lists.newArrayList(); 
    		new ExportExcel("公司资质数据", Companyaptitude.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
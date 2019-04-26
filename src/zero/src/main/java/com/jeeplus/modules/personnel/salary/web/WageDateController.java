/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.web;

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
import com.jeeplus.modules.personnel.salary.entity.WageDate;
import com.jeeplus.modules.personnel.salary.service.WageDateService;

/**
 * 工资发放时间设置Controller
 * @author 王伟
 * @version 2019-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/salary/wageDate")
public class WageDateController extends BaseController {

	@Autowired
	private WageDateService wageDateService;
	
	@ModelAttribute
	public WageDate get(@RequestParam(required=false) String id) {
		WageDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wageDateService.get(id);
		}
		if (entity == null){
			entity = new WageDate();
		}
		return entity;
	}
	
	/**
	 * 时间列表页面
	 */
	@RequiresPermissions("personnel:salary:wageDate:list")
	@RequestMapping(value = {"list", ""})
	public String list(WageDate wageDate, Model model) {
		model.addAttribute("wageDate", wageDate);
		return "modules/personnel/salary/wageDateList";
	}
	
		/**
	 * 时间列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:wageDate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WageDate wageDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WageDate> page = wageDateService.findPage(new Page<WageDate>(request, response), wageDate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑时间表单页面
	 */
	@RequiresPermissions(value={"personnel:salary:wageDate:view","personnel:salary:wageDate:add","personnel:salary:wageDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WageDate wageDate, Model model) {
		model.addAttribute("wageDate", wageDate);
		return "modules/personnel/salary/wageDateForm";
	}

	/**
	 * 保存时间
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:salary:wageDate:add","personnel:salary:wageDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(WageDate wageDate, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(wageDate);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		j= wageDateService.presave(wageDate);//保存
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 删除时间
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:wageDate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WageDate wageDate) {
		AjaxJson j = new AjaxJson();
		wageDateService.delete(wageDate);
		j.setMsg("删除时间成功");
		return j;
	}
	
	/**
	 * 批量删除时间
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:wageDate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wageDateService.delete(wageDateService.get(id));
		}
		j.setMsg("删除时间成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:wageDate:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(WageDate wageDate, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "时间"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WageDate> page = wageDateService.findPage(new Page<WageDate>(request, response, -1), wageDate);
    		new ExportExcel("时间", WageDate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出时间记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:wageDate:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WageDate> list = ei.getDataList(WageDate.class);
			for (WageDate wageDate : list){
				try{
					wageDateService.save(wageDate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条时间记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条时间记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入时间失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入时间数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:wageDate:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "时间数据导入模板.xlsx";
    		List<WageDate> list = Lists.newArrayList(); 
    		new ExportExcel("时间数据", WageDate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
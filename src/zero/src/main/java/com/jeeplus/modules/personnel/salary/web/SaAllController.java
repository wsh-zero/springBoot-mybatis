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
import com.jeeplus.modules.personnel.salary.entity.SaAll;
import com.jeeplus.modules.personnel.salary.service.SaAllService;

/**
 * 薪资账套设置Controller
 * @author 王伟
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/salary/saAll")
public class SaAllController extends BaseController {

	@Autowired
	private SaAllService saAllService;
	
	@ModelAttribute
	public SaAll get(@RequestParam(required=false) String id) {
		SaAll entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = saAllService.get(id);
		}
		if (entity == null){
			entity = new SaAll();
		}
		return entity;
	}
	
	/**
	 * 薪资账套列表页面
	 */
	@RequiresPermissions("personnel:salary:saAll:list")
	@RequestMapping(value = {"list", ""})
	public String list(SaAll saAll, Model model) {
		model.addAttribute("saAll", saAll);
		return "modules/personnel/salary/saAllList";
	}
	
		/**
	 * 薪资账套列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:saAll:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SaAll saAll, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SaAll> page = saAllService.findPage(new Page<SaAll>(request, response), saAll); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑薪资账套表单页面
	 */
	@RequiresPermissions(value={"personnel:salary:saAll:view","personnel:salary:saAll:add","personnel:salary:saAll:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SaAll saAll, Model model) {
		model.addAttribute("saAll", saAll);
		return "modules/personnel/salary/saAllForm";
	}

	/**
	 * 保存薪资账套
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:salary:saAll:add","personnel:salary:saAll:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(SaAll saAll, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(saAll);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		saAllService.save(saAll);//保存
		j.setSuccess(true);
		j.setMsg("保存薪资账套成功");
		return j;
	}
	
	/**
	 * 删除薪资账套
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:saAll:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SaAll saAll) {
		AjaxJson j = new AjaxJson();
		saAllService.delete(saAll);
		j.setMsg("删除薪资账套成功");
		return j;
	}
	
	/**
	 * 批量删除薪资账套
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:saAll:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			saAllService.delete(saAllService.get(id));
		}
		j.setMsg("删除薪资账套成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:saAll:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(SaAll saAll, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "薪资账套"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SaAll> page = saAllService.findPage(new Page<SaAll>(request, response, -1), saAll);
    		new ExportExcel("薪资账套", SaAll.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出薪资账套记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:saAll:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SaAll> list = ei.getDataList(SaAll.class);
			for (SaAll saAll : list){
				try{
					saAllService.save(saAll);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条薪资账套记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条薪资账套记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入薪资账套失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入薪资账套数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:saAll:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "薪资账套数据导入模板.xlsx";
    		List<SaAll> list = Lists.newArrayList(); 
    		new ExportExcel("薪资账套数据", SaAll.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
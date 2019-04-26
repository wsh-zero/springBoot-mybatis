/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.web;

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
import com.jeeplus.modules.personnel.plan.entity.ComSystem;
import com.jeeplus.modules.personnel.plan.service.ComSystemService;

/**
 * 制度管理Controller
 * @author 王伟
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/plan/comSystem")
public class ComSystemController extends BaseController {

	@Autowired
	private ComSystemService comSystemService;
	
	@ModelAttribute
	public ComSystem get(@RequestParam(required=false) String id) {
		ComSystem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comSystemService.get(id);
		}
		if (entity == null){
			entity = new ComSystem();
		}
		return entity;
	}
	
	/**
	 * 制度列表页面
	 */
	@RequiresPermissions("personnel:plan:comSystem:list")
	@RequestMapping(value = {"list", ""})
	public String list(ComSystem comSystem, Model model) {
		model.addAttribute("comSystem", comSystem);
		return "modules/personnel/plan/comSystemList";
	}
	
		/**
	 * 制度列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:comSystem:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComSystem comSystem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComSystem> page = comSystemService.findPage(new Page<ComSystem>(request, response), comSystem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑制度表单页面
	 */
	@RequiresPermissions(value={"personnel:plan:comSystem:view","personnel:plan:comSystem:add","personnel:plan:comSystem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComSystem comSystem, Model model) {
		model.addAttribute("comSystem", comSystem);
		return "modules/personnel/plan/comSystemForm";
	}

	/**
	 * 保存制度
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:plan:comSystem:add","personnel:plan:comSystem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ComSystem comSystem, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(comSystem);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		j = comSystemService.preserve(comSystem);//保存
		return j;
	}
	
	/**
	 * 删除制度
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:comSystem:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComSystem comSystem) {
		AjaxJson j = new AjaxJson();
		comSystemService.delete(comSystem);
		j.setMsg("删除制度成功");
		return j;
	}
	
	/**
	 * 批量删除制度
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:comSystem:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comSystemService.delete(comSystemService.get(id));
		}
		j.setMsg("删除制度成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:comSystem:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(ComSystem comSystem, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "制度"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComSystem> page = comSystemService.findPage(new Page<ComSystem>(request, response, -1), comSystem);
    		new ExportExcel("制度", ComSystem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出制度记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:comSystem:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComSystem> list = ei.getDataList(ComSystem.class);
			for (ComSystem comSystem : list){
				try{
					comSystemService.save(comSystem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条制度记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条制度记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入制度失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入制度数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:comSystem:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "制度数据导入模板.xlsx";
    		List<ComSystem> list = Lists.newArrayList(); 
    		new ExportExcel("制度数据", ComSystem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
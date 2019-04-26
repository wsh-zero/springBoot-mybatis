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
import com.jeeplus.modules.personnel.plan.entity.TitleBonus;
import com.jeeplus.modules.personnel.plan.service.TitleBonusService;

/**
 * 职称Controller
 * @author 王伟
 * @version 2019-03-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/plan/titleBonus")
public class TitleBonusController extends BaseController {

	@Autowired
	private TitleBonusService titleBonusService;
	
	@ModelAttribute
	public TitleBonus get(@RequestParam(required=false) String id) {
		TitleBonus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = titleBonusService.get(id);
		}
		if (entity == null){
			entity = new TitleBonus();
		}
		return entity;
	}
	
	/**
	 * 职称列表页面
	 */
	@RequiresPermissions("personnel:plan:titleBonus:list")
	@RequestMapping(value = {"list", ""})
	public String list(TitleBonus titleBonus, Model model) {
		model.addAttribute("titleBonus", titleBonus);
		return "modules/personnel/plan/titleBonusList";
	}
	
		/**
	 * 职称列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:titleBonus:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TitleBonus titleBonus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TitleBonus> page = titleBonusService.findPage(new Page<TitleBonus>(request, response), titleBonus); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑职称表单页面
	 */
	@RequiresPermissions(value={"personnel:plan:titleBonus:view","personnel:plan:titleBonus:add","personnel:plan:titleBonus:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TitleBonus titleBonus, Model model) {
		model.addAttribute("titleBonus", titleBonus);
		return "modules/personnel/plan/titleBonusForm";
	}

	/**
	 * 保存职称
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:plan:titleBonus:add","personnel:plan:titleBonus:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TitleBonus titleBonus, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(titleBonus);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		try {
			j = titleBonusService.preserve(titleBonus);//保存
			return j;
		}catch (Exception e){
			return j;
		}
	}
	
	/**
	 * 删除职称
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:titleBonus:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TitleBonus titleBonus) {
		AjaxJson j = new AjaxJson();
		titleBonusService.delete(titleBonus);
		j.setMsg("删除职称成功");
		return j;
	}
	
	/**
	 * 批量删除职称
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:titleBonus:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			titleBonusService.delete(titleBonusService.get(id));
		}
		j.setMsg("删除职称成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:titleBonus:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TitleBonus titleBonus, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "职称"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TitleBonus> page = titleBonusService.findPage(new Page<TitleBonus>(request, response, -1), titleBonus);
    		new ExportExcel("职称", TitleBonus.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出职称记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:titleBonus:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TitleBonus> list = ei.getDataList(TitleBonus.class);
			for (TitleBonus titleBonus : list){
				try{
					titleBonusService.save(titleBonus);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条职称记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条职称记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入职称失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入职称数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:titleBonus:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "职称数据导入模板.xlsx";
    		List<TitleBonus> list = Lists.newArrayList(); 
    		new ExportExcel("职称数据", TitleBonus.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
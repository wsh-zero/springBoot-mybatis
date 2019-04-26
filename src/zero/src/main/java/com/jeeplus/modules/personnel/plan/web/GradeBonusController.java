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
import com.jeeplus.modules.personnel.plan.entity.GradeBonus;
import com.jeeplus.modules.personnel.plan.service.GradeBonusService;

/**
 * 等级Controller
 * @author 王伟
 * @version 2019-03-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/plan/gradeBonus")
public class GradeBonusController extends BaseController {

	@Autowired
	private GradeBonusService gradeBonusService;
	
	@ModelAttribute
	public GradeBonus get(@RequestParam(required=false) String id) {
		GradeBonus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gradeBonusService.get(id);
		}
		if (entity == null){
			entity = new GradeBonus();
		}
		return entity;
	}
	
	/**
	 * 等级列表页面
	 */
	@RequiresPermissions("personnel:plan:gradeBonus:list")
	@RequestMapping(value = {"list", ""})
	public String list(GradeBonus gradeBonus, Model model) {
		model.addAttribute("gradeBonus", gradeBonus);
		return "modules/personnel/plan/gradeBonusList";
	}
	
		/**
	 * 等级列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:gradeBonus:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GradeBonus gradeBonus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GradeBonus> page = gradeBonusService.findPage(new Page<GradeBonus>(request, response), gradeBonus); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑等级表单页面
	 */
	@RequiresPermissions(value={"personnel:plan:gradeBonus:view","personnel:plan:gradeBonus:add","personnel:plan:gradeBonus:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GradeBonus gradeBonus, Model model) {
		model.addAttribute("gradeBonus", gradeBonus);
		return "modules/personnel/plan/gradeBonusForm";
	}

	/**
	 * 保存等级
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:plan:gradeBonus:add","personnel:plan:gradeBonus:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GradeBonus gradeBonus, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(gradeBonus);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		try {
			j = gradeBonusService.preserve(gradeBonus);//保存

			return j;
		}catch (Exception e){
			return j;
		}
	}
	
	/**
	 * 删除等级
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:gradeBonus:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GradeBonus gradeBonus) {
		AjaxJson j = new AjaxJson();
		gradeBonusService.delete(gradeBonus);
		j.setMsg("删除等级成功");
		return j;
	}
	
	/**
	 * 批量删除等级
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:gradeBonus:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			gradeBonusService.delete(gradeBonusService.get(id));
		}
		j.setMsg("删除等级成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:gradeBonus:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(GradeBonus gradeBonus, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "等级"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GradeBonus> page = gradeBonusService.findPage(new Page<GradeBonus>(request, response, -1), gradeBonus);
    		new ExportExcel("等级", GradeBonus.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出等级记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:gradeBonus:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GradeBonus> list = ei.getDataList(GradeBonus.class);
			for (GradeBonus gradeBonus : list){
				try{
					gradeBonusService.save(gradeBonus);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条等级记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条等级记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入等级失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入等级数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:gradeBonus:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "等级数据导入模板.xlsx";
    		List<GradeBonus> list = Lists.newArrayList(); 
    		new ExportExcel("等级数据", GradeBonus.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
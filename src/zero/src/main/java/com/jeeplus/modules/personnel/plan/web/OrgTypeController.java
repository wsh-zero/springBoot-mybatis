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
import com.jeeplus.modules.personnel.plan.entity.OrgType;
import com.jeeplus.modules.personnel.plan.service.OrgTypeService;

/**
 * 组织类型Controller
 * @author 王伟
 * @version 2019-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/plan/orgType")
public class OrgTypeController extends BaseController {

	@Autowired
	private OrgTypeService orgTypeService;
	
	@ModelAttribute
	public OrgType get(@RequestParam(required=false) String id) {
		OrgType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orgTypeService.get(id);
		}
		if (entity == null){
			entity = new OrgType();
		}
		return entity;
	}
	
	/**
	 * 组织类型列表页面
	 */
	@RequiresPermissions("personnel:plan:orgType:list")
	@RequestMapping(value = {"list", ""})
	public String list(OrgType orgType, Model model) {
		model.addAttribute("orgType", orgType);
		return "modules/personnel/plan/orgTypeList";
	}
	
		/**
	 * 组织类型列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:orgType:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrgType orgType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrgType> page = orgTypeService.findPage(new Page<OrgType>(request, response), orgType); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑组织类型表单页面
	 */
	@RequiresPermissions(value={"personnel:plan:orgType:view","personnel:plan:orgType:add","personnel:plan:orgType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrgType orgType, Model model) {
		model.addAttribute("orgType", orgType);
		return "modules/personnel/plan/orgTypeForm";
	}

	/**
	 * 保存组织类型
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:plan:orgType:add","personnel:plan:orgType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OrgType orgType, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(orgType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		j = orgTypeService.preserve(orgType);//保存
		return j;
	}
	
	/**
	 * 删除组织类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:orgType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrgType orgType) {
		AjaxJson j = new AjaxJson();
		orgTypeService.delete(orgType);
		j.setMsg("删除组织类型成功");
		return j;
	}
	
	/**
	 * 批量删除组织类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:orgType:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orgTypeService.delete(orgTypeService.get(id));
		}
		j.setMsg("删除组织类型成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:orgType:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(OrgType orgType, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "组织类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrgType> page = orgTypeService.findPage(new Page<OrgType>(request, response, -1), orgType);
    		new ExportExcel("组织类型", OrgType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出组织类型记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:orgType:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrgType> list = ei.getDataList(OrgType.class);
			for (OrgType orgType : list){
				try{
					orgTypeService.save(orgType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条组织类型记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条组织类型记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入组织类型失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入组织类型数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:orgType:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "组织类型数据导入模板.xlsx";
    		List<OrgType> list = Lists.newArrayList(); 
    		new ExportExcel("组织类型数据", OrgType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
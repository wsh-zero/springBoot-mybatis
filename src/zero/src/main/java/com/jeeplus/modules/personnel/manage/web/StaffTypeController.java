/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;
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
import com.jeeplus.modules.personnel.manage.entity.StaffType;
import com.jeeplus.modules.personnel.manage.service.StaffTypeService;

/**
 * 员工类型Controller
 * @author 王伟
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/manage/staffType")
public class StaffTypeController extends BaseController {

	@Autowired
	private StaffTypeService staffTypeService;

	@Autowired
	private StaffService staffService;
	
	@ModelAttribute
	public StaffType get(@RequestParam(required=false) String id) {
		StaffType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = staffTypeService.get(id);
		}
		if (entity == null){
			entity = new StaffType();
		}
		return entity;
	}
	
	/**
	 * 员工类型列表页面
	 */
	@RequiresPermissions("personnel:manage:staffType:list")
	@RequestMapping(value = {"list", ""})
	public String list(StaffType staffType, Model model) {
		model.addAttribute("staffType", staffType);
		return "modules/personnel/manage/staffTypeList";
	}
	
		/**
	 * 员工类型列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffType:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StaffType staffType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StaffType> page = staffTypeService.findPage(new Page<StaffType>(request, response), staffType); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工类型表单页面
	 */
	@RequiresPermissions(value={"personnel:manage:staffType:view","personnel:manage:staffType:add","personnel:manage:staffType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(StaffType staffType, Model model) {
		model.addAttribute("staffType", staffType);
		return "modules/personnel/manage/staffTypeForm";
	}

	/**
	 * 保存员工类型
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:manage:staffType:add","personnel:manage:staffType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(StaffType staffType, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(staffType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		return staffTypeService.preserve(staffType);//保存
	}
	
	/**
	 * 删除员工类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StaffType staffType) {
		AjaxJson j = new AjaxJson();
		staffTypeService.delete(staffType);
		j.setMsg("删除员工类型成功");
		return j;
	}
	
	/**
	 * 批量删除员工类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffType:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			StaffType staffType = new StaffType();
			staffType.setId(id);
			Staff staff = new Staff();
			staff.setStaffType(staffType);
			int count = staffService.count(staff);
			if (count > 0){
				StaffType s = staffTypeService.get(id);
				j.setSuccess(false);
				j.setMsg("当前员工类型"+s.getType()+"使用中，无法删除");
				return j;
			}
			staffTypeService.delete(staffTypeService.get(id));
		}
		j.setMsg("删除员工类型成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffType:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(StaffType staffType, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StaffType> page = staffTypeService.findPage(new Page<StaffType>(request, response, -1), staffType);
    		new ExportExcel("员工类型", StaffType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工类型记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffType:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StaffType> list = ei.getDataList(StaffType.class);
			for (StaffType staffType : list){
				try{
					staffTypeService.save(staffType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工类型记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工类型记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工类型失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工类型数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffType:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工类型数据导入模板.xlsx";
    		List<StaffType> list = Lists.newArrayList(); 
    		new ExportExcel("员工类型数据", StaffType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
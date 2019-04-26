/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.web;

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
import com.jeeplus.modules.personnel.manage.entity.LeaveType;
import com.jeeplus.modules.personnel.manage.service.LeaveTypeService;

/**
 * 离职类型Controller
 * @author 王伟
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/manage/leaveType")
public class LeaveTypeController extends BaseController {

	@Autowired
	private LeaveTypeService leaveTypeService;
	
	@ModelAttribute
	public LeaveType get(@RequestParam(required=false) String id) {
		LeaveType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = leaveTypeService.get(id);
		}
		if (entity == null){
			entity = new LeaveType();
		}
		return entity;
	}
	
	/**
	 * 离职类型列表页面
	 */
	@RequiresPermissions("personnel:manage:leaveType:list")
	@RequestMapping(value = {"list", ""})
	public String list(LeaveType leaveType, Model model) {
		model.addAttribute("leaveType", leaveType);
		return "modules/personnel/manage/leaveTypeList";
	}
	
		/**
	 * 离职类型列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:leaveType:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LeaveType leaveType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LeaveType> page = leaveTypeService.findPage(new Page<LeaveType>(request, response), leaveType); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑离职类型表单页面
	 */
	@RequiresPermissions(value={"personnel:manage:leaveType:view","personnel:manage:leaveType:add","personnel:manage:leaveType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LeaveType leaveType, Model model) {
		model.addAttribute("leaveType", leaveType);
		return "modules/personnel/manage/leaveTypeForm";
	}

	/**
	 * 保存离职类型
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:manage:leaveType:add","personnel:manage:leaveType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(LeaveType leaveType, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(leaveType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		j  = leaveTypeService.preserve(leaveType);//保存
		return j;

	}
	
	/**
	 * 删除离职类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:leaveType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LeaveType leaveType) {
		AjaxJson j = new AjaxJson();
		leaveTypeService.delete(leaveType);
		j.setMsg("删除离职类型成功");
		return j;
	}
	
	/**
	 * 批量删除离职类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:leaveType:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			leaveTypeService.delete(leaveTypeService.get(id));
		}
		j.setMsg("删除离职类型成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:leaveType:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(LeaveType leaveType, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "离职类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LeaveType> page = leaveTypeService.findPage(new Page<LeaveType>(request, response, -1), leaveType);
    		new ExportExcel("离职类型", LeaveType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出离职类型记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:leaveType:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LeaveType> list = ei.getDataList(LeaveType.class);
			for (LeaveType leaveType : list){
				try{
					leaveTypeService.save(leaveType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条离职类型记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条离职类型记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入离职类型失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入离职类型数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:leaveType:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "离职类型数据导入模板.xlsx";
    		List<LeaveType> list = Lists.newArrayList(); 
    		new ExportExcel("离职类型数据", LeaveType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
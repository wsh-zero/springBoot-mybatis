/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.manage.service.StaffStatusService;

/**
 * 员工状态Controller
 * @author 王伟
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/manage/staffStatus")
public class StaffStatusController extends BaseController {

	@Autowired
	private StaffStatusService staffStatusService;

	@Autowired
	private StaffService staffService;
	
	@ModelAttribute
	public StaffStatus get(@RequestParam(required=false) String id) {
		StaffStatus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = staffStatusService.get(id);
		}
		if (entity == null){
			entity = new StaffStatus();
		}
		return entity;
	}
	
	/**
	 * 员工状态列表页面
	 */
	@RequiresPermissions("personnel:manage:staffStatus:list")
	@RequestMapping(value = {"list", ""})
	public String list(StaffStatus staffStatus, Model model) {
		model.addAttribute("staffStatus", staffStatus);
		return "modules/personnel/manage/staffStatusList";
	}
	
		/**
	 * 员工状态列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffStatus:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StaffStatus staffStatus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StaffStatus> page = staffStatusService.findPage(new Page<StaffStatus>(request, response), staffStatus); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工状态表单页面
	 */
	@RequiresPermissions(value={"personnel:manage:staffStatus:view","personnel:manage:staffStatus:add","personnel:manage:staffStatus:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(StaffStatus staffStatus, Model model) {
		model.addAttribute("staffStatus", staffStatus);
		return "modules/personnel/manage/staffStatusForm";
	}

	/**
	 * 保存员工状态
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:manage:staffStatus:add","personnel:manage:staffStatus:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(StaffStatus staffStatus, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(staffStatus);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		j =staffStatusService.preserve(staffStatus);//保存

		return j;
	}
	
	/**
	 * 删除员工状态
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffStatus:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StaffStatus staffStatus) {
		AjaxJson j = new AjaxJson();
		staffStatusService.delete(staffStatus);
		j.setMsg("删除员工状态成功");
		return j;
	}
	
	/**
	 * 批量删除员工状态
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffStatus:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			StaffStatus status = new StaffStatus();
			status.setId(id);
			Staff staff = new Staff();
			staff.setStatus(status);
			int count = staffService.count(staff);
			if (count > 0){
				StaffStatus s = staffStatusService.get(id);
				j.setSuccess(false);
				j.setMsg("当前员工状态"+s.getStatus()+"使用中，无法删除");
				return j;
			}
			staffStatusService.delete(staffStatusService.get(id));
		}
		j.setMsg("删除员工状态成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffStatus:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(StaffStatus staffStatus, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工状态"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StaffStatus> page = staffStatusService.findPage(new Page<StaffStatus>(request, response, -1), staffStatus);
    		new ExportExcel("员工状态", StaffStatus.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工状态记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffStatus:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StaffStatus> list = ei.getDataList(StaffStatus.class);
			for (StaffStatus staffStatus : list){
				try{
					staffStatusService.save(staffStatus);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工状态记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工状态记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工状态失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工状态数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:staffStatus:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工状态数据导入模板.xlsx";
    		List<StaffStatus> list = Lists.newArrayList(); 
    		new ExportExcel("员工状态数据", StaffStatus.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.attendance.web;

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
import com.jeeplus.modules.personnel.attendance.entity.AttendanceType;
import com.jeeplus.modules.personnel.attendance.service.AttendanceTypeService;

/**
 * 考勤类型Controller
 * @author 王伟
 * @version 2019-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/attendance/attendanceType")
public class AttendanceTypeController extends BaseController {

	@Autowired
	private AttendanceTypeService attendanceTypeService;
	@Autowired
	private StaffService staffService;
	
	@ModelAttribute
	public AttendanceType get(@RequestParam(required=false) String id) {
		AttendanceType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attendanceTypeService.get(id);
		}
		if (entity == null){
			entity = new AttendanceType();
		}
		return entity;
	}
	
	/**
	 * 考勤类型列表页面
	 */
	@RequiresPermissions("personnel:attendance:attendanceType:list")
	@RequestMapping(value = {"list", ""})
	public String list(AttendanceType attendanceType, Model model) {
		model.addAttribute("attendanceType", attendanceType);
		return "modules/personnel/attendance/attendanceTypeList";
	}
	
		/**
	 * 考勤类型列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:attendance:attendanceType:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(AttendanceType attendanceType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AttendanceType> page = attendanceTypeService.findPage(new Page<AttendanceType>(request, response), attendanceType); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑考勤类型表单页面
	 */
	@RequiresPermissions(value={"personnel:attendance:attendanceType:view","personnel:attendance:attendanceType:add","personnel:attendance:attendanceType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AttendanceType attendanceType, Model model) {
		model.addAttribute("attendanceType", attendanceType);
		return "modules/personnel/attendance/attendanceTypeForm";
	}

	/**
	 * 保存考勤类型
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:attendance:attendanceType:add","personnel:attendance:attendanceType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(AttendanceType attendanceType, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(attendanceType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		attendanceTypeService.save(attendanceType);//保存
		j.setSuccess(true);
		j.setMsg("保存考勤类型成功");
		return j;
	}
	
	/**
	 * 删除考勤类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:attendance:attendanceType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(AttendanceType attendanceType) {
		AjaxJson j = new AjaxJson();
		attendanceTypeService.delete(attendanceType);
		j.setMsg("删除考勤类型成功");
		return j;
	}
	
	/**
	 * 批量删除考勤类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:attendance:attendanceType:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			AttendanceType attendanceType = new AttendanceType();
			attendanceType.setId(id);
			Staff staff = new Staff();
//			staff.setAttendance(attendanceType);
			int count = staffService.count(staff);
			if (count > 0){
				j.setSuccess(false);
				j.setMsg("当前类型使用中,无法删除");
				return j;
			}
			attendanceTypeService.delete(attendanceTypeService.get(id));
		}
		j.setMsg("删除考勤类型成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:attendance:attendanceType:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(AttendanceType attendanceType, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "考勤类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<AttendanceType> page = attendanceTypeService.findPage(new Page<AttendanceType>(request, response, -1), attendanceType);
    		new ExportExcel("考勤类型", AttendanceType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出考勤类型记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:attendance:attendanceType:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AttendanceType> list = ei.getDataList(AttendanceType.class);
			for (AttendanceType attendanceType : list){
				try{
					attendanceTypeService.save(attendanceType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条考勤类型记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条考勤类型记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入考勤类型失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入考勤类型数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:attendance:attendanceType:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "考勤类型数据导入模板.xlsx";
    		List<AttendanceType> list = Lists.newArrayList(); 
    		new ExportExcel("考勤类型数据", AttendanceType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
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
import com.jeeplus.modules.personnel.salary.entity.PenaltyRecord;
import com.jeeplus.modules.personnel.salary.service.PenaltyRecordService;

/**
 * 行政处罚记录Controller
 * @author 王伟
 * @version 2019-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/salary/penaltyRecord")
public class PenaltyRecordController extends BaseController {

	@Autowired
	private PenaltyRecordService penaltyRecordService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private StaffService staffService;
	
	@ModelAttribute
	public PenaltyRecord get(@RequestParam(required=false) String id) {
		PenaltyRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = penaltyRecordService.get(id);
		}
		if (entity == null){
			entity = new PenaltyRecord();
		}
		return entity;
	}
	
	/**
	 * 处罚记录列表页面
	 */
	@RequiresPermissions("personnel:salary:penaltyRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list(PenaltyRecord penaltyRecord, Model model) {
		model.addAttribute("penaltyRecord", penaltyRecord);
		return "modules/personnel/salary/penaltyRecordList";
	}
	
		/**
	 * 处罚记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:penaltyRecord:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PenaltyRecord penaltyRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PenaltyRecord> page = penaltyRecordService.findPage(new Page<PenaltyRecord>(request, response), penaltyRecord);
		List<PenaltyRecord> list = page.getList();
		for (PenaltyRecord record:list){
			User user = userMapper.get(record.getName());
			if (user.getStaff()!=null){
				Staff staff = staffService.get(user.getStaff().getId());
				record.setCode(staff);
			}
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑处罚记录表单页面
	 */
	@RequiresPermissions(value={"personnel:salary:penaltyRecord:view","personnel:salary:penaltyRecord:add","personnel:salary:penaltyRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PenaltyRecord penaltyRecord, Model model) {
		model.addAttribute("penaltyRecord", penaltyRecord);
		return "modules/personnel/salary/penaltyRecordForm";
	}

	/**
	 * 保存处罚记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:salary:penaltyRecord:add","personnel:salary:penaltyRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PenaltyRecord penaltyRecord, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(penaltyRecord);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		penaltyRecordService.save(penaltyRecord);//保存
		j.setSuccess(true);
		j.setMsg("保存处罚记录成功");
		return j;
	}
	
	/**
	 * 删除处罚记录
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:penaltyRecord:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PenaltyRecord penaltyRecord) {
		AjaxJson j = new AjaxJson();
		penaltyRecordService.delete(penaltyRecord);
		j.setMsg("删除处罚记录成功");
		return j;
	}
	
	/**
	 * 批量删除处罚记录
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:penaltyRecord:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			penaltyRecordService.delete(penaltyRecordService.get(id));
		}
		j.setMsg("删除处罚记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:penaltyRecord:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(PenaltyRecord penaltyRecord, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "处罚记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PenaltyRecord> page = penaltyRecordService.findPage(new Page<PenaltyRecord>(request, response, -1), penaltyRecord);
    		new ExportExcel("处罚记录", PenaltyRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出处罚记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:penaltyRecord:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PenaltyRecord> list = ei.getDataList(PenaltyRecord.class);
			for (PenaltyRecord penaltyRecord : list){
				try{
					penaltyRecordService.save(penaltyRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条处罚记录记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条处罚记录记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入处罚记录失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入处罚记录数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:penaltyRecord:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "处罚记录数据导入模板.xlsx";
    		List<PenaltyRecord> list = Lists.newArrayList(); 
    		new ExportExcel("处罚记录数据", PenaltyRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
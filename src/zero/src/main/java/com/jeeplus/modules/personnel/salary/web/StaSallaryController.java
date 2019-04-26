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

import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.manage.service.StaffStatusService;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;
import com.jeeplus.modules.personnel.salary.entity.SaAll;
import com.jeeplus.modules.personnel.salary.service.SaAllService;
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
import com.jeeplus.modules.personnel.salary.entity.StaSallary;
import com.jeeplus.modules.personnel.salary.service.StaSallaryService;

/**
 * 员工薪资配置Controller
 * @author 王伟
 * @version 2019-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/salary/staSallary")
public class StaSallaryController extends BaseController {

	@Autowired
	private StaSallaryService staSallaryService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private StaffStatusService staffStatusService;
	@Autowired
	private SaAllService saAllService;

	@ModelAttribute
	public StaSallary get(@RequestParam(required=false) String id) {
		StaSallary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = staSallaryService.get(id);
		}
		if (entity == null){
			entity = new StaSallary();
		}
		return entity;
	}

	/**
	 * 员工薪资配置列表页面
	 */
	@RequiresPermissions("personnel:salary:staSallary:list")
	@RequestMapping(value = {"list", ""})
	public String list(StaSallary staSallary, Model model) {
		model.addAttribute("staSallary", staSallary);
		List<SaAll> saAll = saAllService.findList(new SaAll());
		model.addAttribute("saAll",saAll);
		List<StaffStatus> staffStatus = staffStatusService.findList(new StaffStatus());
		model.addAttribute("staffStatus",staffStatus);
		return "modules/personnel/salary/staSallaryList";
	}

	/**
	 * 员工薪资配置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:staSallary:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StaSallary staSallary, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StaSallary> page = staSallaryService.findPage(new Page<StaSallary>(request, response), staSallary);
		List<StaSallary> sta = page.getList();
		for (StaSallary sallary : sta){
			Staff staff = sallary.getName();
			Staff s = staffService.get(staff);
			sallary.setStatus(s.getStatus());
			sallary.setCode(s);
			sallary.setDepart(s.getDepart());
			sallary.setStation(s.getStation());
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工薪资配置表单页面
	 */
	@RequiresPermissions(value={"personnel:salary:staSallary:view","personnel:salary:staSallary:add","personnel:salary:staSallary:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(StaSallary staSallary, Model model) {
		model.addAttribute("staSallary", staSallary);
		List<StaffStatus> staffStatus = staffStatusService.findList(new StaffStatus());
		model.addAttribute("staffStatus",staffStatus);
		List<SaAll> saAll = saAllService.findList(new SaAll());
		model.addAttribute("saAll",saAll);
		return "modules/personnel/salary/staSallaryForm";
	}

	/**
	 * 保存员工薪资配置
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:salary:staSallary:add","personnel:salary:staSallary:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(StaSallary staSallary, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(staSallary);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		staSallaryService.save(staSallary);//保存
		j.setSuccess(true);
		j.setMsg("保存员工薪资配置成功");
		return j;
	}

	/**
	 * 删除员工薪资配置
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:staSallary:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StaSallary staSallary) {
		AjaxJson j = new AjaxJson();
		staSallaryService.delete(staSallary);
		j.setMsg("删除员工薪资配置成功");
		return j;
	}

	/**
	 * 批量删除员工薪资配置
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:staSallary:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			staSallaryService.delete(staSallaryService.get(id));
		}
		j.setMsg("删除员工薪资配置成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:staSallary:export")
	@RequestMapping(value = "export")
	public AjaxJson exportFile(StaSallary staSallary, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "员工薪资配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<StaSallary> page = staSallaryService.findPage(new Page<StaSallary>(request, response, -1), staSallary);
			new ExportExcel("员工薪资配置", StaSallary.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工薪资配置记录失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:staSallary:import")
	@RequestMapping(value = "import")
	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StaSallary> list = ei.getDataList(StaSallary.class);
			for (StaSallary staSallary : list){
				try{
					staSallaryService.save(staSallary);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工薪资配置记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工薪资配置记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工薪资配置失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 下载导入员工薪资配置数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:staSallary:import")
	@RequestMapping(value = "import/template")
	public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "员工薪资配置数据导入模板.xlsx";
			List<StaSallary> list = Lists.newArrayList();
			new ExportExcel("员工薪资配置数据", StaSallary.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
	}

}
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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.jeeplus.modules.personnel.salary.entity.PaySlip;
import com.jeeplus.modules.personnel.salary.service.PaySlipService;

/**
 * 工资条Controller
 * @author ww
 * @version 2019-03-29
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/salary/paySlip")
public class PaySlipController extends BaseController {

	@Autowired
	private PaySlipService paySlipService;
	@Autowired
	private StaffService staffService;
	
	@ModelAttribute
	public PaySlip get(@RequestParam(required=false) String id) {
		PaySlip entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = paySlipService.get(id);
		}
		if (entity == null){
			entity = new PaySlip();
		}
		return entity;
	}
	
	/**
	 * 工资条列表页面
	 */
	@RequiresPermissions("personnel:salary:paySlip:list")
	@RequestMapping(value = {"list", ""})
	public String list(PaySlip paySlip,HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("paySlip", paySlip);
		model.addAttribute("isSelf", false);
		return "modules/personnel/salary/paySlipList";
	}
	/**
	 * 修改状态
	 */
	@RequestMapping(value = "editStatus")
	public String editStatus(@RequestParam String id ,@RequestParam String status ,Model model){
		AjaxJson j =new AjaxJson();
		PaySlip paySlip = paySlipService.get(id);
		paySlip.setStatus(status);
		paySlipService.save(paySlip);
		model.addAttribute("paySlip", paySlip);
		return "redirect:list";
	}
		/**
	 * 工资条列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:paySlip:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PaySlip paySlip,boolean isSelf, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PaySlip> page = null;
		if (isSelf){
			paySlip.setSelf(true);
		}
		page =  paySlipService.findPage(new Page<PaySlip>(request, response), paySlip);
		for (PaySlip slip :page.getList()){
			Staff staff = staffService.get(slip.getName().getId());
			slip.setCode(staff);
			slip.setDepart(staff.getDepart());
			slip.setStation(staff.getStation());
		}
		return getBootstrapData(page);
	}
	/**
	 * 我的通知列表
	 */
	@RequestMapping(value = "self")
	public String selfList(PaySlip paySlip, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("isSelf", true);
		return "modules/personnel/salary/paySlipList";
	}

	/**
	 * 查看，增加，编辑工资条表单页面
	 */
	@RequiresPermissions(value={"personnel:salary:paySlip:view","personnel:salary:paySlip:add","personnel:salary:paySlip:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode,boolean isSelf, PaySlip paySlip, Model model) {
		model.addAttribute("paySlip", paySlip);
		model.addAttribute("mode", mode);
		model.addAttribute("isSelf",isSelf);
		return "modules/personnel/salary/paySlipForm";
	}

	/**
	 * 保存工资条
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:salary:paySlip:add","personnel:salary:paySlip:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PaySlip paySlip, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(paySlip);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		paySlipService.save(paySlip);//保存
		j.setSuccess(true);
		j.setMsg("保存工资条成功");
		return j;
	}
	
	/**
	 * 删除工资条
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:paySlip:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PaySlip paySlip) {
		AjaxJson j = new AjaxJson();
		paySlipService.delete(paySlip);
		j.setMsg("删除工资条成功");
		return j;
	}
	
	/**
	 * 批量删除工资条
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:paySlip:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			paySlipService.delete(paySlipService.get(id));
		}
		j.setMsg("删除工资条成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:paySlip:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(PaySlip paySlip, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工资条"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PaySlip> page = paySlipService.findPage(new Page<PaySlip>(request, response, -1), paySlip);
			for (PaySlip slip :page.getList()){
				Staff staff = staffService.get(slip.getName().getId());
				slip.setCode(staff);
				slip.setDepart(staff.getDepart());
				slip.setStation(staff.getStation());

			}
    		new ExportExcel("工资条", PaySlip.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工资条记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:paySlip:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PaySlip> list = ei.getDataList(PaySlip.class);
			for (PaySlip paySlip : list){
				try{
					paySlipService.save(paySlip);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工资条记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条工资条记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入工资条失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入工资条数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:paySlip:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工资条数据导入模板.xlsx";
    		List<PaySlip> list = Lists.newArrayList(); 
    		new ExportExcel("工资条数据", PaySlip.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
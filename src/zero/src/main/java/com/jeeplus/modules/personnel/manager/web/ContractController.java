/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;


import com.jeeplus.modules.personnel.manage.entity.ContractType;
import com.jeeplus.modules.personnel.manage.service.ContractTypeService;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;
import com.jeeplus.modules.personnel.manager.util.NumberUtil;
import com.jeeplus.modules.personnel.vo.ContractVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
import com.jeeplus.modules.personnel.manager.entity.Contract;
import com.jeeplus.modules.personnel.manager.service.ContractService;

/**
 * 合同管理Controller
 * @author 王伟
 * @version 2019-02-11
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/manager/contract")
public class ContractController extends BaseController {

	@Autowired
	private ContractService contractService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private ContractTypeService contractTypeService;
	@Autowired
	private NumberUtil numberUtil;
	@ModelAttribute
	public Contract get(@RequestParam(required=false) String id) {
		Contract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractService.get(id);
		}
		if (entity == null){
			entity = new Contract();
		}
		return entity;
	}
	
	/**
	 * 员工合同列表页面
	 */
	@RequiresPermissions("personnel:manager:contract:list")
	@RequestMapping(value = {"list", ""})
	public String list(Contract contract, Model model) {
		model.addAttribute("contract", contract);
		model.addAttribute("type",contractTypeService.findList(new ContractType()));
		return "modules/personnel/manager/contractList";
	}
	
		/**
	 * 员工合同列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contract:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Staff s = contract.getStaffName();
		s.setDepart(contract.getDepart());
		s.setStation(contract.getStation());
//        s.setContractType(contract.getContractType());
		contract.setStaffName(s);
 		Page<Contract> page = contractService.findPage(new Page<Contract>(request, response), contract);
		if (page.getList().size()>0) {
			for (Contract con : page.getList()) {
				Staff sta = con.getStaffName();
				Staff staff = staffService.get(sta.getId());
//				con.setContractType(staff.getContractType());
				con.setSex(staff.getSex());
				con.setStaffCode(staff);
				con.setDepart(staff.getDepart());
				con.setIdCard(staff);
				con.setContactType(staff);
				con.setStation(staff.getStation());
			}
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工合同表单页面
	 */
	@RequiresPermissions(value={"personnel:manager:contract:view","personnel:manager:contract:add","personnel:manager:contract:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Contract contract, Model model) {
//		if ("add".equals(mode)) {
//			String maxnum = numberUtil.getMaxnumber();
//			contract.setContractCode(numberUtil.AGREECODE + maxnum);  //合同编号
//			contract.setSecretCode(numberUtil.SECRECYCODE + maxnum);  //保密协议编号
//			contract.setNumber(maxnum);
//		}
		model.addAttribute("type",contractTypeService.findList(new ContractType()));
		model.addAttribute("contract", contract);
		return "modules/personnel/manager/contractForm";
	}

	/**
	 * 保存员工合同
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:manager:contract:add","personnel:manager:contract:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Contract contract, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(contract);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		int a = contract.getContractEnd().compareTo(contract.getContractStart());
		int b = contract.getSecretEnd().compareTo(contract.getContractStart());
		if (a<=0){
			j.setSuccess(false);
			j.setMsg("合同结束时间不能小于或等于开始时间");
			return j;
		}
		if (b<=0){
			j.setSuccess(false);
			j.setMsg("协议结束时间不能小于或等于开始时间");
			return j;
		}
//		if (contract.getStaffName()!=null) {
//			Staff sta1 = contract.getStaffName();
//			Staff sta = staffService.get(sta1);
//			DecimalFormat countFormat = new DecimalFormat("0000");
//			Integer num = Integer.valueOf(sta.getNumber());
//			String numb = countFormat.format(num);
//
//			contract.setContractCode(numberUtil.AGREECODE + numb);
//			contract.setSecretCode(numberUtil.SECRECYCODE + numb);
//		}
		//新增或编辑表单保存
		if (contract.getIsNewRecord()) {
			if (contract.getStaffName() != null) {
				Staff staff = contract.getStaffName();
				Contract contract1 = contractService.getName(staff.getId());
				if (contract1 != null) {
					j.setSuccess(false);
					j.setMsg("员工合同信息已存在");
					return j;
				}
			}

			if (StringUtils.isNotBlank(contract.getContractCode())) {
				Contract con = new Contract();
				con.setContractCode(contract.getContractCode());
				int count = contractService.count(con);
				if (count > 0) {
					j.setSuccess(false);
					j.setMsg("合同编号重复");
					return j;
				}
			}
			if (StringUtils.isNotBlank(contract.getSecretCode())) {
				Contract con = new Contract();
				con.setSecretCode(contract.getSecretCode());
				int count = contractService.count(con);
				if (count > 0) {
					j.setSuccess(false);
					j.setMsg("保密协议编号重复");
					return j;
				}
			}
		}else{
			if (contract.getStaffName() != null) {
				Staff staff = contract.getStaffName();
				System.out.println(staff.getId().toString());
				Contract contract1 = contractService.getName(staff.getId());
				if (contract1!=null&&!contract1.getId().equals(contract.getId())) {
					j.setSuccess(false);
					j.setMsg("员工合同信息已存在");
					return j;
				}
			}
			if (StringUtils.isNotBlank(contract.getContractCode())) {
				Contract con = new Contract();
				con.setContractCode(contract.getContractCode());
				Contract contract1 = contractService.find(con);
				if (contract1!=null&&!contract1.getId().equals(contract.getId())) {
					j.setSuccess(false);
					j.setMsg("合同编号重复");
					return j;
				}
			}
			if (StringUtils.isNotBlank(contract.getSecretCode())) {
				Contract con = new Contract();
				con.setSecretCode(contract.getSecretCode());
				Contract contract1 = contractService.find(con);
				if (contract1!=null&&!contract1.getId().equals(contract.getId())) {
					j.setSuccess(false);
					j.setMsg("保密协议编号重复");
					return j;
				}
			}
		}

		contractService.save(contract);//保存
		j.setSuccess(true);
		j.setMsg("保存员工合同成功");
		return j;
	}
	
	/**
	 * 删除员工合同
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contract:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Contract contract) {
		AjaxJson j = new AjaxJson();
		contractService.delete(contract);
		j.setMsg("删除员工合同成功");
		return j;
	}
	
	/**
	 * 批量删除员工合同
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contract:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			contractService.delete(contractService.get(id));
		}
		j.setMsg("删除员工合同成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contract:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Contract contract, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工合同"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Contract> page = contractService.findPage(new Page<Contract>(request, response, -1), contract);
			if (page.getList().size()>0){
				for(Contract con:page.getList()){
					Staff sta = con.getStaffName();
					Staff staff = staffService.get(sta.getId());
//					con.setContractType(staff.getContractType());
					con.setSex(staff.getSex());
					con.setStaffCode(staff);
					con.setDepart(staff.getDepart());
					con.setIdCard(staff);
					con.setContactType(staff);
					con.setStation(staff.getStation());
				}
			}
    		new ExportExcel("员工合同", Contract.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工合同记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contract:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ContractVo> list = ei.getDataList(ContractVo.class);
			for (ContractVo contractVo : list){
				try{
					Staff s = new Staff();
					s.setCode(contractVo.getStaffCode());
					Staff staff = staffService.find(s);
					Contract contract = new Contract();
					contract.setStaffName(staff);
					contract.setContractCode(contractVo.getContractCode());
					contract.setSecretCode(contractVo.getSecretCode());
					contract.setContractEnd(contractVo.getContractEnd());
					contract.setContractStart(contractVo.getContractStart());
					contract.setSecretStart(contractVo.getSecretStart());
					contract.setSecretEnd(contractVo.getSecretEnd());
					contract.setSignNumber(contractVo.getSignNumber());
					contractService.save(contract);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工合同记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工合同记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工合同失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工合同数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contract:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工合同数据导入模板.xlsx";
    		List<Contract> list = Lists.newArrayList(); 
    		new ExportExcel("员工合同数据", Contract.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
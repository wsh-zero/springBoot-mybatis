/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.web;

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
import com.jeeplus.modules.personnel.vo.SalaryCardVo;
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
import com.jeeplus.modules.personnel.manager.entity.SalaryCard;
import com.jeeplus.modules.personnel.manager.service.SalaryCardService;

/**
 * 工资卡Controller
 * @author 王伟
 * @version 2019-01-31
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/manager/salaryCard")
public class SalaryCardController extends BaseController {

	@Autowired
	private SalaryCardService salaryCardService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private StaffStatusService staffStatusService;
	
	@ModelAttribute
	public SalaryCard get(@RequestParam(required=false) String id) {
		SalaryCard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = salaryCardService.get(id);
		}
		if (entity == null){
			entity = new SalaryCard();
		}
		return entity;
	}
	
	/**
	 * 工资卡列表页面
	 */
	@RequiresPermissions("personnel:manager:salaryCard:list")
	@RequestMapping(value = {"list", ""})
	public String list(SalaryCard salaryCard, Model model) {
		model.addAttribute("salaryCard", salaryCard);
		model.addAttribute("staffStatus",staffStatusService.findList(new StaffStatus()));
		return "modules/personnel/manager/salaryCardList";
	}
	
		/**
	 * 工资卡列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:salaryCard:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SalaryCard salaryCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Staff s = salaryCard.getName();
		s.setDepart(salaryCard.getDepart());
		s.setStation(salaryCard.getStation());
		s.setStatus(salaryCard.getStaffStatus());
		salaryCard.setName(s);
		Page<SalaryCard> page = salaryCardService.findPage(new Page<SalaryCard>(request, response), salaryCard);
		if (page.getList().size()>0){
			for (SalaryCard sa : page.getList()){
				Staff sta = sa.getName();
				Staff staff = staffService.get(sta.getId());
				if (StringUtils.isNotBlank(staff.getContactType()))
				sa.setContactType(staff);
				if (staff.getStation()!=null)
				sa.setStation(staff.getStation());
				sa.setDepart(staff.getDepart());
				sa.setIdCard(staff);
				sa.setCode(staff);
				sa.setSex(staff.getSex());
				sa.setStaffStatus(staff.getStatus());
			}
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工资卡表单页面
	 */
	@RequiresPermissions(value={"personnel:manager:salaryCard:view","personnel:manager:salaryCard:add","personnel:manager:salaryCard:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SalaryCard salaryCard, Model model) {
		model.addAttribute("salaryCard", salaryCard);
		return "modules/personnel/manager/salaryCardForm";
	}

	/**
	 * 保存工资卡
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:manager:salaryCard:add","personnel:manager:salaryCard:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(SalaryCard salaryCard, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(salaryCard);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if (salaryCard.getIsNewRecord()) {
			if (salaryCard.getName() != null) {
				Staff staff = salaryCard.getName();
				SalaryCard salaryCard1 = salaryCardService.getName(staff.getId());
				if (salaryCard1 != null) {
					j.setSuccess(false);
					j.setMsg("员工工资卡信息已存在");
					return j;
				}
			}

			SalaryCard salary = new SalaryCard();
			if (StringUtils.isNotBlank(salaryCard.getBankCard())) {
				salary.setBankCard(salaryCard.getBankCard());
				int count = salaryCardService.count(salaryCard);
				if (count > 0) {
					j.setSuccess(false);
					j.setMsg("银行卡号重复");
					return j;
				}
			}
		}else {
			if (salaryCard.getName() != null) {
				Staff staff = salaryCard.getName();
				SalaryCard salaryCard1 = salaryCardService.getName(staff.getId());
				if (salaryCard1 != null&&!salaryCard.getId().equals(salaryCard1.getId())) {
					j.setSuccess(false);
					j.setMsg("员工工资卡信息已存在");
					return j;
				}
			}
			if (StringUtils.isNotBlank(salaryCard.getBankCard())) {
				SalaryCard salary = new SalaryCard();
				salary.setBankCard(salaryCard.getBankCard());
				SalaryCard salaryCard1 = salaryCardService.find(salaryCard);
				if (salaryCard1 != null && !salaryCard.getId().equals(salaryCard1.getId()) ) {
					j.setSuccess(false);
					j.setMsg("银行卡号重复");
					return j;
				}
			}
		}
		salaryCardService.save(salaryCard);//保存
		j.setSuccess(true);
		j.setMsg("保存工资卡成功");
		return j;
	}
	
	/**
	 * 删除工资卡
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:salaryCard:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SalaryCard salaryCard) {
		AjaxJson j = new AjaxJson();
		salaryCardService.delete(salaryCard);
		j.setMsg("删除工资卡成功");
		return j;
	}
	
	/**
	 * 批量删除工资卡
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:salaryCard:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			salaryCardService.delete(salaryCardService.get(id));
		}
		j.setMsg("删除工资卡成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:salaryCard:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(SalaryCard salaryCard, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工资卡"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SalaryCard> page = salaryCardService.findPage(new Page<SalaryCard>(request, response, -1), salaryCard);
			if (page.getList().size()>0){
				for (SalaryCard sa : page.getList()){
					Staff sta = sa.getName();
					Staff staff = staffService.get(sta.getId());
					sa.setContactType(staff);
					sa.setStation(staff.getStation());
					sa.setDepart(staff.getDepart());
					sa.setIdCard(staff);
					sa.setSex(staff.getSex());
					sa.setStaffStatus(staff.getStatus());
					sa.setCode(staff);
				}
			}
    		new ExportExcel("工资卡", SalaryCard.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工资卡记录失败！失败信息："+e.getMessage());
		}
		return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:salaryCard:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SalaryCardVo> list = ei.getDataList(SalaryCardVo.class);
			for (int i = 0; i < list.size(); i++) {
				try {
					Staff s = new Staff();
					SalaryCard salaryCard = new SalaryCard();
					if (StringUtils.isBlank(list.get(i).getIdCard())){
						failureMsg.append("第"+i+2+"行，身份证号为空");
					}
					Staff staff1 = new Staff();
					staff1.setIdCard(list.get(i).getIdCard());
					Staff staff2 =staffService.find(staff1);
					SalaryCard salaryCard1 = new SalaryCard();
					salaryCard1.setName(staff2);
					SalaryCard salaryCard2 = salaryCardService.find(salaryCard1);
					if (salaryCard2!=null){
						failureMsg.append("第"+i+2+"行，身份证号不能重复");
					}

					s.setIdCard(list.get(i).getIdCard());
					Staff staff = staffService.find(s);


					if (StringUtils.isBlank(list.get(i).getBankCard())){
						failureMsg.append("第"+i+2+"行，银行卡号不能为空");
					}
					SalaryCard salaryCard3 = new SalaryCard();
					salaryCard3.setBankCard(list.get(i).getBankCard());
					SalaryCard salaryCard4 = salaryCardService.find(salaryCard3);
					if (salaryCard4!=null){
						failureMsg.append("第"+i+2+"行，银行卡号不能重复");
					}
					salaryCard.setBankCard(list.get(i).getBankCard());
					salaryCard.setBank(list.get(i).getBank());
					if (StringUtils.isBlank(list.get(i).getSalaryStatus())){
						failureMsg.append("第"+i+2+"行，银行卡状态不能为空");
					}
					salaryCard.setSalaryStatus(list.get(i).getSalaryStatus());
					salaryCard.setName(staff);
					salaryCardService.save(salaryCard);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}

		}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工资卡记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条工资卡记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入工资卡失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入工资卡数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:salaryCard:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工资卡数据导入模板.xlsx";
    		List<SalaryCard> list = Lists.newArrayList(); 
    		new ExportExcel("工资卡数据", SalaryCard.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
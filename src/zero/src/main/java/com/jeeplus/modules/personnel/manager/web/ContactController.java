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

import com.jeeplus.modules.personnel.manager.entity.SalaryCard;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;
import com.jeeplus.modules.personnel.vo.ContactVo;
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
import com.jeeplus.modules.personnel.manager.entity.Contact;
import com.jeeplus.modules.personnel.manager.service.ContactService;

/**
 * 员工通讯录Controller
 * @author 王伟
 * @version 2019-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/manager/contact")
public class ContactController extends BaseController {

	@Autowired
	private ContactService contactService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private UserMapper userMapper;

	@ModelAttribute
	public Contact get(@RequestParam(required=false) String id) {
		Contact entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contactService.get(id);
		}
		if (entity == null){
			entity = new Contact();
		}
		return entity;
	}

	/**
	 * 通讯录列表页面
	 */
	@RequiresPermissions("personnel:manager:contact:list")
	@RequestMapping(value = {"list", ""})
	public String list(Contact contact, Model model) {
		model.addAttribute("contact", contact);
		return "modules/personnel/manager/contactList";
	}

	/**
	 * 通讯录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contact:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Contact contact, HttpServletRequest request, HttpServletResponse response, Model model) {
		Staff s = contact.getName();
		s.setCompany(contact.getCompany());
		s.setDepart(contact.getDepart());
		s.setStation(contact.getStation());
		contact.setName(s);
		Page<Contact> page = contactService.findPage(new Page<Contact>(request, response), contact);
		if (page.getList().size()>0){
			for (Contact con :page.getList()){
				Staff sta = con.getName();
				Staff staff = staffService.get(sta.getId());
				if (staff.getStation()!=null)
				con.setStation(staff.getStation());
				con.setDepart(staff.getDepart());
				con.setCompany(staff.getCompany());
				if (staff.getContactType()!=null)
				con.setContactType(staff);
				con.setStaffCode(staff);
			}
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑通讯录表单页面
	 */
	@RequiresPermissions(value={"personnel:manager:contact:view","personnel:manager:contact:add","personnel:manager:contact:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Contact contact, Model model) {
		model.addAttribute("contact", contact);
		return "modules/personnel/manager/contactForm";
	}

	/**
	 * 保存通讯录
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:manager:contact:add","personnel:manager:contact:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Contact contact, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(contact);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if (contact.getIsNewRecord()) {
			if (contact.getName() != null) {
				Staff staff = contact.getName();
				Contact contact1 = contactService.getName(staff.getId());
				if (contact1 != null) {
					j.setSuccess(false);
					j.setMsg("员工通讯录已存在");
					return j;
				}
			}


			if (StringUtils.isNotBlank(contact.getEmail())) {
				Contact con = new Contact();
				con.setEmail(contact.getEmail());
				int count = contactService.count(con);
				if ( count > 0) {
					j.setSuccess(false);
					j.setMsg("员工个人邮箱重复");
					return j;
				}
			}

			if (StringUtils.isNotBlank(contact.getQq())) {
				Contact con = new Contact();
				con.setQq(contact.getQq());
				int count = contactService.count(con);
				if (count > 0) {
					j.setSuccess(false);
					j.setMsg("员工QQ重复");
					return j;
				}
			}
			if (StringUtils.isNotBlank(contact.getWeChat())) {
				Contact con = new Contact();
				con.setWeChat(contact.getWeChat());
				int count = contactService.count(con);
				if ( count > 1) {
					j.setSuccess(false);
					j.setMsg("员工微信重复");
					return j;
				}
			}
		}else {
			Staff s = contact.getName();
			User user = new User();
			user.setStaff(s);
			user.setEmail(contact.getEmail());
			userMapper.updateByStaff(user);

			if (contact.getName() != null) {
				Staff staff = contact.getName();
				Contact contact1 = contactService.getName(staff.getId());
				if (contact1!=null&&!contact1.getId().equals(contact.getId())) {
					j.setSuccess(false);
					j.setMsg("员工通讯录已存在");
					return j;
				}
			}
			if (StringUtils.isNotBlank(contact.getEmail())) {
				Contact con = new Contact();
				con.setEmail(contact.getEmail());
				Contact contact1 = contactService.find(con);
				if (contact1 != null && !contact1.getId().equals(contact.getId())) {
					j.setSuccess(false);
					j.setMsg("员工个人邮箱重复");
					return j;
				}
			}

			if (StringUtils.isNotBlank(contact.getQq())) {
				Contact con = new Contact();
				con.setQq(contact.getQq());
				Contact contact1 = contactService.find(con);
				if (contact1!=null&&!contact1.getId().equals(contact.getId())) {
					j.setSuccess(false);
					j.setMsg("员工QQ重复");
					return j;
				}
			}
			if (StringUtils.isNotBlank(contact.getWeChat())) {
				Contact con = new Contact();
				con.setWeChat(contact.getWeChat());
				Contact contact1 = contactService.find(con);
				if (contact1!=null&&!contact1.getId().equals(contact.getId())) {
					j.setSuccess(false);
					j.setMsg("员工微信重复");
					return j;
				}
			}
		}

		contactService.save(contact);//保存
		j.setSuccess(true);
		j.setMsg("保存通讯录成功");
		return j;
	}

	/**
	 * 删除通讯录
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contact:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Contact contact) {
		AjaxJson j = new AjaxJson();
		contactService.delete(contact);
		j.setMsg("删除通讯录成功");
		return j;
	}

	/**
	 * 批量删除通讯录
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contact:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			contactService.delete(contactService.get(id));
		}
		j.setMsg("删除通讯录成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contact:export")
	@RequestMapping(value = "export")
	public AjaxJson exportFile(Contact contact, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "通讯录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Contact> page = contactService.findPage(new Page<Contact>(request, response, -1), contact);
			if (page.getList().size()>0) {
				for (Contact con : page.getList()) {
					Staff s = con.getName();
					Staff staff = staffService.get(s.getId());
					con.setStation(staff.getStation());
					con.setDepart(staff.getDepart());
					con.setCompany(staff.getCompany());
					con.setContactType(staff);
					con.setStaffCode(staff);
				}
			}
			new ExportExcel("通讯录", Contact.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出通讯录记录失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contact:import")
	@RequestMapping(value = "import")
	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ContactVo> list = ei.getDataList(ContactVo.class);
			for (int i =0;i<list.size();i++){
				try{
					Staff s = new Staff();
					if (StringUtils.isBlank(list.get(i).getStaffCode())){
						failureMsg.append("第"+i+2+"行，员工编号不能为空,");
					}
					Staff staff1 = new Staff();
					staff1.setCode(list.get(i).getStaffCode());
					Staff staff2 = staffService.find(staff1);
					Contact contact1 = new Contact();
					contact1.setName(staff2);
					Contact contact2 = contactService.find(contact1);
					if (contact2!=null){
						failureMsg.append("第"+i+2+"行，员工编号不能重复,");
					}
					s.setCode(list.get(i).getStaffCode());

					Staff staff = staffService.find(s);

					Contact contact = new Contact();
					if (StringUtils.isBlank(list.get(i).getEmail())){
						failureMsg.append("第"+i+2+"行，个人邮箱不能为空,");
					}
					Contact contact3 = new Contact();
					contact3.setEmail(list.get(i).getEmail());
					Contact contact4 = contactService.find(contact3);
					if (contact4!=null){
						failureMsg.append("第"+i+2+"行，个人邮箱不能重复,");
					}
					contact.setEmail(list.get(i).getEmail());

					contact.setName(staff);
					Contact contact5 = new Contact();

					if (StringUtils.isBlank(list.get(i).getQq())){
						failureMsg.append("第"+i+2+"行，QQ不能为空,");
					}
					contact5.setQq(list.get(i).getQq());
					Contact contact6 = contactService.find(contact5);
					if (contact6!=null){
						failureMsg.append("第"+i+2+"行，QQ不能重复,");
					}
					contact.setQq(list.get(i).getQq());
					if (StringUtils.isBlank(list.get(i).getWeChat())){
						failureMsg.append("第"+i+2+"行，微信不能为空,");
					}
					Contact contact7 = new Contact();
					contact7.setWeChat(list.get(i).getWeChat());
					Contact contact8 = contactService.find(contact7);
					if (contact8!=null){
						failureMsg.append("第"+i+2+"行，微信不能重复,");
					}
					contact.setWeChat(list.get(i).getWeChat());
					if (StringUtils.isBlank(list.get(i).getStatus())){
						failureMsg.append("第"+i+2+"行，微信不能为空,");
					}

					contact.setStatus(list.get(i).getStatus());
					contactService.save(contact);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条通讯录记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条通讯录记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入通讯录失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 下载导入通讯录数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:contact:import")
	@RequestMapping(value = "import/template")
	public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "通讯录数据导入模板.xlsx";
			List<Contact> list = Lists.newArrayList();
			new ExportExcel("通讯录数据", Contact.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
	}

}
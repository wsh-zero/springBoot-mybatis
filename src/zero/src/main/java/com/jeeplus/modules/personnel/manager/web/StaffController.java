/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.personnel.attendance.mapper.AttendanceTypeMapper;
import com.jeeplus.modules.personnel.manage.entity.StaffStatus;
import com.jeeplus.modules.personnel.manage.entity.StaffType;
import com.jeeplus.modules.personnel.manage.mapper.ContractTypeMapper;
import com.jeeplus.modules.personnel.manage.mapper.StaffStatusMapper;
import com.jeeplus.modules.personnel.manage.mapper.StaffTypeMapper;
import com.jeeplus.modules.personnel.manager.util.NumberUtil;
import com.jeeplus.modules.personnel.plan.entity.Education;
import com.jeeplus.modules.personnel.plan.entity.Rank;
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.personnel.plan.mapper.EducationMapper;
import com.jeeplus.modules.personnel.plan.mapper.OrgMapper;
import com.jeeplus.modules.personnel.plan.mapper.RankMapper;
import com.jeeplus.modules.personnel.plan.mapper.StationMapper;
import com.jeeplus.modules.personnel.vo.StaffVo;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;

/**
 * 员工基本信息Controller
 * @author ww
 * @version 2019-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/manager/staff")
public class StaffController extends BaseController {
	@Autowired
	private NumberUtil numberUtil;
	@Autowired
	private StaffService staffService;
	@Autowired
	private ContractTypeMapper contractTypeMapper;
	@Autowired
	private StaffStatusMapper staffStatusMapper;
	@Autowired
	private AttendanceTypeMapper attendanceTypeMapper;
	@Autowired
	private StaffTypeMapper staffTypeMapper;
	@Autowired
	private StationMapper stationMapper;
	@Autowired
	private RankMapper rankMapper;
	@Autowired
	private EducationMapper educationMapper;
	@Autowired
	private UserMapper userMapper;

	private static OrgMapper orgMapper = SpringContextHolder.getBean(OrgMapper.class);

	@ModelAttribute
	public Staff get(@RequestParam(required=false) String id) {
		Staff entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = staffService.get(id);
			if (entity != null) {
				if (entity.getEntryDate() != null) {
					Date date = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(entity.getEntryDate());
					int result = calendar.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH);
					int month = (calendar.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR)) * 12;
					int count = Math.abs(result + month);
					int year = count / 12;
					if (year <= 0 && count > 0) {
						entity.setWorkYear(count + "月");
					}
					if (count == 0) {
						entity.setWorkYear("不足一月");
					}
					if (year > 0 && count > 0) {
						int a = count - 12 * year;
						if (a > 0) {
							entity.setWorkYear(year + "年" + count + "月");
						}
						if (a == 0) {
							entity.setWorkYear(year + "年");
						}
					}

				}
			}
		}

		if (entity == null){
			entity = new Staff();
		}
		return entity;
	}
//	/**
//	 * 导入Excel数据
//
//	 */
//	@ResponseBody
//	@RequiresPermissions("personnel:manager:staff:import")
//	@RequestMapping(value = "import")
//	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		try {
//			int successNum = 0;
//			int failureNum = 0;
//			StringBuilder failureMsg = new StringBuilder();
//			ImportExcel ei = new ImportExcel(file, 1, 0);
//			List<StaffVo> list = ei.getDataList(StaffVo.class);
//			for (int i =0; i<list.size();i++) {
//
//				try {
//					Staff staff = new Staff();
//					if (StringUtils.isNotBlank(list.get(i).getCompany())) {
//						Office office = UserUtils.getByOrgName(list.get(i).getCompany());
//						staff.setCompany(office);
//					}
//					if (StringUtils.isNotBlank(list.get(i).getDepart())) {
//						Office office1 = UserUtils.getByOrgName(list.get(i).getDepart());
//						staff.setDepart(office1);
//					}
//
//					String maxnum = numberUtil.getMaxnumber();
//					staff.setCode(numberUtil.STALLCODE + maxnum);   //员工编号
//					staff.setNumber(maxnum);
//
//					if (StringUtils.isNotBlank(list.get(i).getStatus())) {
//						StaffStatus status = new StaffStatus();
//						status.setStatus(list.get(i).getStatus());
//						StaffStatus staffStatus = staffStatusMapper.find(status);
//						staff.setStatus(staffStatus);
//					}
//					if (StringUtils.isNotBlank(list.get(i).getStation())) {
//						Station station = new Station();
//						station.setName(list.get(i).getStation());
//						Station station1 = stationMapper.find(station);
//						staff.setStation(station1);
//					}
//					if (StringUtils.isNotBlank(list.get(i).getStaffType())) {
//						StaffType staffType = new StaffType();
//						staffType.setType(list.get(i).getStaffType());
//						StaffType staffType1 = staffTypeMapper.find(staffType);
//						staff.setStaffType(staffType1);
//					}
//
//					if (StringUtils.isNotBlank(list.get(i).getRank())) {
//						Rank r = new Rank();
//						r.setRankName(list.get(i).getRank());
//						Rank rank = rankMapper.find(r);
//						staff.setRank(rank);
//					}
//					if (StringUtils.isNotBlank(list.get(i).getEducation())){
//					Education education = new Education();
//					education.setEducate(list.get(i).getEducation());
//					Education e = educationMapper.find(education);
//					staff.setEducation(e);
//				    }
//                    if (list.get(i).getEntryDate()!=null) {
//						staff.setEntryDate(list.get(i).getEntryDate());
//					}
//					if (list.get(i).getMarriage()!=null) {
//						staff.setMarriage(list.get(i).getMarriage());
//					}
//					if (list.get(i).getContactType()!=null) {
//						staff.setContactType(list.get(i).getContactType());
//					}
//					if (list.get(i).getSex()!=null) {
//						staff.setSex(list.get(i).getSex());
//					}
//					if (list.get(i).getName()!=null) {
//						staff.setName(list.get(i).getName());
//					}
//					if (list.get(i).getBirthDate()!=null) {
//						staff.setBirthDate(list.get(i).getBirthDate());
//					}
//					if (StringUtils.isNotBlank(list.get(i).getIdCard())) {
//						staff.setIdCard(list.get(i).getIdCard());
//					}
//					if (StringUtils.isNotBlank(list.get(i).getSocialSecurity())){
//						staff.setSocialSecurity(list.get(i).getSocialSecurity());
//					}
//					if (StringUtils.isNotBlank(list.get(i).getDetailPlace())){
//						staff.setDetailPlace(list.get(i).getDetailPlace());
//					}
//					if (list.get(i).getGraduationTime()!=null){
//						staff.setGraduationTime(list.get(i).getGraduationTime());
//					}
//					if (StringUtils.isNotBlank(list.get(i).getPolitical())){
//					    staff.setPolitical(list.get(i).getPolitical());
//					}
//					if (StringUtils.isNotBlank(list.get(i).getNation())){
//						staff.setNation(list.get(i).getNation());
//					}
//					if (StringUtils.isNotBlank(list.get(i).getMajor())){
//						staff.setMajor(list.get(i).getMajor());
//					}
//					if (StringUtils.isNotBlank(list.get(i).getGraduation())){
//						staff.setGraduation(list.get(i).getGraduation());
//					}
//					staffService.save(staff);
//					successNum++;
//				} catch (ConstraintViolationException ex) {
//					failureNum++;
//				} catch (Exception ex) {
//					failureNum++;
//				}
//
//			}
//			if (failureNum>0){
//				failureMsg.insert(0, "，失败 "+failureNum+" 条员工信息记录。");
//				j.setMsg("已成功导入 "+successNum+" 条员工信息记录,");
//				return j;
//			}
//			j.setMsg( "已成功导入 "+successNum+" 条员工信息记录,");
//		} catch (Exception e) {
//			j.setSuccess(false);
//			j.setMsg("导入员工信息失败！失败信息："+e.getMessage());
//		}
//		return j;
//	}
	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:staff:import")
	@RequestMapping(value = "import")
	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StaffVo> list = ei.getDataList(StaffVo.class);
			for (int i =0; i<list.size();i++) {

					try {
						Staff staff = new Staff();
						Office office = UserUtils.getByOrgName(list.get(i).getCompany());
						staff.setCompany(office);
						Office office1 = UserUtils.getByOrgName(list.get(i).getDepart());
						staff.setDepart(office1);

						String maxnum = numberUtil.getMaxnumber();
						staff.setCode(numberUtil.STALLCODE + maxnum);   //员工编号
						staff.setNumber(maxnum);

//					ContractType contractType = new ContractType();
//					contractType.setName(vo.getContractType());
//					ContractType contractType1 = contractTypeMapper.find(contractType);
//					staff.setContractType(contractType1);

						StaffStatus status = new StaffStatus();
						status.setStatus(list.get(i).getStatus());
						StaffStatus staffStatus = staffStatusMapper.find(status);
						staff.setStatus(staffStatus);

						Station station = new Station();
						station.setName(list.get(i).getStation());
						Station station1 = stationMapper.find(station);
						staff.setStation(station1);

						StaffType staffType = new StaffType();
						staffType.setType(list.get(i).getStaffType());
						StaffType staffType1 = staffTypeMapper.find(staffType);
						staff.setStaffType(staffType1);

//					AttendanceType attendanceType = new AttendanceType();
//					attendanceType.setName(vo.getAttendance());
//					AttendanceType type = attendanceTypeMapper.find(attendanceType);
//					staff.setAttendance(type);

						Rank r = new Rank();
						r.setRankName(list.get(i).getRank());
						Rank rank = rankMapper.find(r);
						staff.setRank(rank);

						Education education = new Education();
						education.setEducate(list.get(i).getEducation());
						Education e = educationMapper.find(education);
						staff.setEducation(e);

						staff.setEntryDate(list.get(i).getEntryDate());
						staff.setMarriage(list.get(i).getMarriage());
						staff.setContactType(list.get(i).getContactType());
						staff.setSex(list.get(i).getSex());
						staff.setName(list.get(i).getName());
						staff.setBirthDate(list.get(i).getBirthDate());

						if (StringUtils.isBlank(list.get(i).getIdCard())) {
							failureMsg.append("第"+i+2+"行，身份证号不能为空,");
						}
						Staff staff3 = new Staff();
						staff3.setIdCard(list.get(i).getIdCard());
						Staff staff2 = staffService.find(staff3);
						if (staff2 != null) {
							failureMsg.append("第"+i+2+"行，身份证号重复,");
						}
						staff.setIdCard(list.get(i).getIdCard());
						try {
							Staff staff1 = staffService.findLeader(list.get(i).getLeader());
							staff.setLeader(staff1);
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						staffService.save(staff);
						successNum++;
					} catch (ConstraintViolationException ex) {
						failureNum++;
					} catch (Exception ex) {
						failureNum++;
					}

			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工信息记录。");
				j.setMsg("已成功导入 "+successNum+" 条员工信息记录,"+failureMsg);
				return j;
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工信息记录,"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工信息失败！失败信息："+e.getMessage());
		}
		return j;
	}
	/**
	 * 员工信息列表页面
	 */
	@RequiresPermissions("personnel:manager:staff:list")
	@RequestMapping(value = {"list",""})
	public String list(Staff staff, Model model) {
		model.addAttribute("staff", staff);
		model.addAttribute("education",educationMapper.findList(new Education()));
		model.addAttribute("status",staffStatusMapper.findList(new StaffStatus()));
		model.addAttribute("rank",rankMapper.findList(new Rank()));
		model.addAttribute("type",staffTypeMapper.findList(new StaffType()));
		return "modules/personnel/manager/staffList";
	}
//	/**
//	 * 合同信息列表页面
//	 */
//	@RequiresPermissions("personnel:manager:contractInfo:list")
//	@RequestMapping(value = {"infolist"})
//	public String contractInfoList(Staff staff, Model model) {
//		model.addAttribute("staff", staff);
//		return "modules/personnel/manager/contractInfoList";
//	}

	/**
	 * 员工信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:staff:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Staff staff, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Staff> page = staffService.findPage(new Page<Staff>(request, response), staff);
        List<Staff> list =  page.getList();
		for (Staff s :list){
			if (s.getEntryDate()!=null) {
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(s.getEntryDate());
				int result = calendar.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH);
				int month = (calendar.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR)) * 12;
				int count = Math.abs(result+month);
                int year = count/12;
				if (year<=0 && count>0 ){
					s.setWorkYear(count+"月");
				}
				if (count == 0){
					s.setWorkYear("不足一月");
				}
				if (year>0&&count>0){
					int a = count - 12*year;
					if (a>0) {
						s.setWorkYear(year + "年" + a + "月");
					}
					if (a==0){
						s.setWorkYear(year+"年");
					}
				}
			}
		}
		return getBootstrapData(page);
	}

//	/**
//	 * 合同信息列表数据
//	 */
//	@ResponseBody
//	@RequiresPermissions("personnel:manager:contractInfo:list")
//	@RequestMapping(value = "info")
//	public Map<String, Object> contractInfodata(Staff staff, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<Staff> page = staffService.findPage(new Page<Staff>(request, response), staff);
//		return getBootstrapData(page);
//	}

	/**
	 * 查看，增加，编辑员工信息表单页面
	 */
	@RequiresPermissions(value={"personnel:manager:staff:view","personnel:manager:staff:add","personnel:manager:staff:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Staff staff, Model model) {
		if ("add".equals(mode)) {
			String maxnum = numberUtil.getMaxnumber();
			staff.setCode(numberUtil.STALLCODE + maxnum);   //员工编号
//			staff.setContractCode(numberUtil.AGREECODE + maxnum);  //合同编号
//			staff.setSecretCode(numberUtil.SECRECYCODE + maxnum);  //保密协议编号
			staff.setNumber(maxnum);
		}

		model.addAttribute("education",educationMapper.findList(new Education()));
		model.addAttribute("status",staffStatusMapper.findList(new StaffStatus()));
		model.addAttribute("rank",rankMapper.findList(new Rank()));
		model.addAttribute("type",staffTypeMapper.findList(new StaffType()));
		model.addAttribute("staff", staff);
		model.addAttribute("mode", mode);
		if ("add1".equals(mode)){
			return "modules/resume/staffForm";
		}
		return "modules/personnel/manager/staffForm";
	}

	/**
	 * 保存员工信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:manager:staff:add","personnel:manager:staff:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Staff staff, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(staff);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
         if (staff.getLeader().getId()!=null&&staff.getLeader().getId()!=""){
	           Staff s = staff.getLeader();
			      if (staff.getId()==s.getId()&&staff.getId().equals(s.getId()))
			        {
				 j.setSuccess(false);
				 j.setMsg("上级领导不能选择自己");
				 return j;
			 }
            }
		//新增或编辑表单保存
		if (staff.getIsNewRecord()) {
			if (staff.getCode() != null) {
				Staff s = new Staff();
				s.setCode(staff.getCode());
				Staff staff1 = staffService.find(s);
				if (staff1 != null) {
					j.setSuccess(false);
					j.setMsg("员工编号重复");
					return j;
				}
			}
			if (StringUtils.isNotBlank(staff.getSocialSecurity())){
				Staff s = new Staff();
				s.setSocialSecurity(staff.getSocialSecurity());
				Staff staff1 = staffService.find(s);
				if (staff1!=null){
					j.setSuccess(false);
					j.setMsg("社保号码重复");
					return j;
				}
			}

			if (StringUtils.isNotBlank(staff.getContactType())) {
				Staff s = new Staff();
				s.setContactType(staff.getContactType());
				Staff staff1 = staffService.find(s);
				if (staff1 != null) {
					j.setSuccess(false);
					j.setMsg("员工联系方式重复");
					return j;
				}
			}
			if (staff.getIdCard() != null) {
				Staff s = new Staff();
				s.setIdCard(staff.getIdCard());
				Staff staff1 = staffService.find(s);
				if (staff1 != null) {
					j.setSuccess(false);
					j.setMsg("员工身份证号重复");
					return j;
				}
			}
		}else{
			if (StringUtils.isNotBlank(staff.getName())){
				User user = new User();
				user.setName(staff.getName());
				user.setMobile(staff.getContactType());
				user.setStaff(staff);
				userMapper.updateByStaff(user);
			}

			if (staff.getCode() != null) {
				Staff s = new Staff();
				s.setCode(staff.getCode());
				Staff staff1 = staffService.find(s);
				if (staff1 != null && !staff1.getId().equals(staff.getId())) {
					j.setSuccess(false);
					j.setMsg("员工编号重复");
					return j;
				}
			}
            if (StringUtils.isNotBlank(staff.getSocialSecurity())){
				Staff s = new Staff();
				s.setSocialSecurity(staff.getSocialSecurity());
				Staff staff1 = staffService.find(s);
				if (staff1!=null && !staff1.getId().equals(staff.getId())){
					j.setSuccess(false);
					j.setMsg("社保号码重复");
					return j;
				}
			}

			if (staff.getContactType() != null) {
				Staff s = new Staff();
				s.setContactType(staff.getContactType());
				Staff staff1 = staffService.find(s);
				if (staff1 != null && !staff1.getId().equals(staff.getId())) {
					j.setSuccess(false);
					j.setMsg("员工联系方式重复");
					return j;
				}
				User user = userMapper.getUserByStaff(staff.getId());
				if (user!=null) {
					user.setStaff(staff);
					user.setMobile(staff.getContactType());
					userMapper.updateByStaff(user);
				}
			}
			if (staff.getIdCard() != null) {
				Staff s = new Staff();
				s.setIdCard(staff.getIdCard());
				Staff staff1 = staffService.find(s);
				if (staff1 != null && !staff1.getId().equals(staff.getId())) {
					j.setSuccess(false);
					j.setMsg("员工身份证号重复");
					return j;
				}
			}
		}

		staffService.save(staff);//保存
		j.setSuccess(true);
		j.setMsg("保存员工信息成功");
		return j;
	}

	/**
	 * 删除员工信息
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:staff:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Staff staff) {
		AjaxJson j = new AjaxJson();
		staffService.delete(staff);
		j.setMsg("删除员工信息成功");
		return j;
	}

	/**
	 * 批量删除员工信息
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:staff:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			staffService.delete(staffService.get(id));
		}
		j.setMsg("删除员工信息成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:staff:export")
	@RequestMapping(value = "export")
	public AjaxJson exportFile(Staff staff, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "员工信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Staff> page = staffService.findPage(new Page<Staff>(request, response, -1), staff);
			new ExportExcel("员工信息", Staff.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工信息记录失败！失败信息："+e.getMessage());
		}
		return j;
	}



	/**
	 * 下载导入员工信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manager:staff:import")
	@RequestMapping(value = "import/template")
	public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "员工信息数据导入模板.xlsx";
			List<Staff> list = Lists.newArrayList();
			new ExportExcel("员工信息数据", Staff.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
	}

}
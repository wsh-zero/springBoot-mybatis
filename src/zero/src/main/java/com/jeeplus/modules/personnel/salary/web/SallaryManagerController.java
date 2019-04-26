/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;
import com.jeeplus.modules.personnel.plan.entity.GradeBonus;
import com.jeeplus.modules.personnel.plan.entity.TitleBonus;
import com.jeeplus.modules.personnel.plan.mapper.GradeBonusMapper;
import com.jeeplus.modules.personnel.plan.mapper.TitleBonusMapper;
import com.jeeplus.modules.personnel.plan.service.GradeBonusService;
import com.jeeplus.modules.personnel.plan.service.TitleBonusService;
import com.jeeplus.modules.personnel.vo.SallaryManagerVo;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.personnel.salary.entity.SallaryManager;
import com.jeeplus.modules.personnel.salary.service.SallaryManagerService;

/**
 * 员工薪酬管理Controller
 * @author 王伟
 * @version 2019-03-18
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/salary/sallaryManager")
public class SallaryManagerController extends BaseController {

	@Autowired
	private SallaryManagerService sallaryManagerService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private GradeBonusService gradeBonusService;
	@Autowired
	private GradeBonusMapper gradeBonusMapper;
	@Autowired
	private TitleBonusMapper titleBonusMapper;
	@Autowired
	private TitleBonusService titleBonusService;
	@Autowired
	private UserMapper userMapper;
	
	@ModelAttribute
	public SallaryManager get(@RequestParam(required=false) String id) {
		SallaryManager entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sallaryManagerService.get(id);
		}
		if (entity == null){
			entity = new SallaryManager();
		}
		return entity;
	}
	
	/**
	 * 员工薪酬列表页面
	 */
	@RequiresPermissions("personnel:salary:sallaryManager:list")
	@RequestMapping(value = {"list", ""})
	public String list(SallaryManager sallaryManager, Model model) {
		model.addAttribute("sallaryManager", sallaryManager);
		return "modules/personnel/salary/sallaryManagerList";
	}
	
		/**
	 * 员工薪酬列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:sallaryManager:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SallaryManager sallaryManager, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SallaryManager> page = sallaryManagerService.findPage(new Page<SallaryManager>(request, response), sallaryManager);
		List<SallaryManager> sta = page.getList();
		for (SallaryManager sallary : sta){
			Staff staff = sallary.getName();
			Staff s = staffService.get(staff);
			sallary.setStatus(s.getStatus());
			sallary.setCode(s);
			if (sallary.getGrade()!=null) {
				GradeBonus gradeBonus = gradeBonusMapper.find(sallary.getGrade());
				sallary.setGradeBonus(gradeBonus);
			}
			if (sallary.getTitle()!=null) {
				TitleBonus titleBonus = titleBonusMapper.find(sallary.getTitle());
				sallary.setTitleBonus(titleBonus);
			}
			sallary.setStation(s.getStation());
			sallary.setDepart(s.getDepart());
            sallary.setUpdateBy(userMapper.get(sallary.getUpdateBy().getId()));

		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工薪酬表单页面
	 */
	@RequiresPermissions(value={"personnel:salary:sallaryManager:view","personnel:salary:sallaryManager:add","personnel:salary:sallaryManager:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form( SallaryManager sallaryManager, Model model) {
		model.addAttribute("sallaryManager", sallaryManager);
		return "modules/personnel/salary/sallaryManagerForm";
	}

	/**
	 * 保存员工薪酬
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:salary:sallaryManager:add","personnel:salary:sallaryManager:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(SallaryManager sallaryManager, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(sallaryManager);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		sallaryManagerService.save(sallaryManager);//保存
		j.setSuccess(true);
		j.setMsg("保存员工薪酬成功");
		return j;
	}

	/**
	 * 删除员工薪酬
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:sallaryManager:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SallaryManager sallaryManager) {
		AjaxJson j = new AjaxJson();
		sallaryManagerService.delete(sallaryManager);
		j.setMsg("删除员工薪酬成功");
		return j;
	}
	
	/**
	 * 批量删除员工薪酬
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:sallaryManager:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sallaryManagerService.delete(sallaryManagerService.get(id));
		}
		j.setMsg("删除员工薪酬成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:sallaryManager:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(SallaryManager sallaryManager, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {

            String fileName = "员工薪酬"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SallaryManager> page = sallaryManagerService.findPage(new Page<SallaryManager>(request, response, -1), sallaryManager);
            for (SallaryManager manager :page.getList()){
            	if (manager.getTitle()!=null){
            		manager.setTitleBonus(titleBonusMapper.find(manager.getTitle()));
				}
				if (manager.getGrade()!=null){
            		manager.setGradeBonus(gradeBonusMapper.find(manager.getGrade()));
				}
            	SallaryManager manager1 = sallaryManagerService.get(manager.getId());
            	 Staff s = manager1.getName();
            	 s = staffService.get(s.getId());
            	 manager.setCode(s);
            	 manager.setDepart(s.getDepart());
            	 manager.setStation(s.getStation());
            	 manager.setStatus(s.getStatus());
			}
    		new ExportExcel("员工薪酬", SallaryManager.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工薪酬记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:sallaryManager:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SallaryManagerVo> list = ei.getDataList(SallaryManagerVo.class);
			SallaryManager sallaryManager = new SallaryManager();
			for (SallaryManagerVo sallaryManagerVo : list){
				try{
					Staff staff = new Staff();
					staff.setCode(sallaryManagerVo.getCode());
					staff = staffService.find(staff);
					sallaryManager.setName(staff);
					sallaryManager = sallaryManagerService.find(sallaryManager);

					sallaryManager.setAccumulation(Double.parseDouble(sallaryManagerVo.getAccumulation()));
					sallaryManager.setEducationBonus(Double.parseDouble(sallaryManagerVo.getEducationBonus()));
					sallaryManager.setPreWage(Double.parseDouble(sallaryManagerVo.getPreWage()));
					sallaryManager.setSocialSecurity(Double.parseDouble(sallaryManagerVo.getSocialSecurity()));
					sallaryManager.setQualityBonus(Double.parseDouble(sallaryManagerVo.getQualityBonus()));
					sallaryManager.setDisplayBonus(Double.parseDouble(sallaryManagerVo.getDisplayBonus()));
					sallaryManager.setIsNewRecord(false);

					GradeBonus gradeBonus = new GradeBonus();
					gradeBonus.setGrade(sallaryManagerVo.getGrade());
					gradeBonus = gradeBonusMapper.find(gradeBonus);
					sallaryManager.setGrade(gradeBonus);

					TitleBonus titleBonus = new TitleBonus();
					titleBonus.setName(sallaryManagerVo.getTitle());
					titleBonus = titleBonusMapper.find(titleBonus);
					sallaryManager.setTitle(titleBonus);

					sallaryManagerService.save(sallaryManager);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工薪酬记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工薪酬记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工薪酬失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工薪酬数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:salary:sallaryManager:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工薪酬数据导入模板.xlsx";
    		List<SallaryManager> list = Lists.newArrayList();
    		new ExportExcel("员工薪酬数据", SallaryManager.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }


//	/**
//	 * 下载导入员工薪酬数据模板
//	 */
//	@ResponseBody
//	@RequiresPermissions("personnel:salary:sallaryManager:import")
//	@RequestMapping(value = "import/template")
//	public AjaxJson importFileTemplate(HttpServletRequest request, HttpServletResponse response) {
//		List<String[]> downData = new ArrayList<>();
//		AjaxJson j = new AjaxJson();
//		try {
//			String fileName = "员工薪酬数据导入模板.xlsx";
//			String[] handers = {"员工编号","姓名","部门","岗位","员工状态","税前工资","社保","公积金","职称","等级","资质奖金","基础绩效奖金"}; //列标题
//			List<SallaryManager> list = Lists.newArrayList();
//
//			List<GradeBonus> grade = gradeBonusService.getGrade();
//			String[] str1 = new String[grade.size()];
//			grade.toArray(str1);
//
//			List<TitleBonus> title = titleBonusService.getTitle();
//			String[] str2 = new String[title.size()];
//			grade.toArray(str2);
//
//			downData.add(str1);
//			downData.add(str2);
//
//			String [] downRows = {"8","9"}; //下拉的列序号数组(序号从0开始)
//
//			(fileName, handers, downData, downRows, request, response);
//
//			return null;
//		} catch (Exception e) {
//			j.setSuccess(false);
//			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
//		}
//		return j;
//	}
}
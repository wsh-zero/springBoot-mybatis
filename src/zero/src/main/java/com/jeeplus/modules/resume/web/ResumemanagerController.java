/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resume.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import com.jeeplus.modules.sys.service.OfficeService;
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
import com.jeeplus.modules.resume.entity.Resumemanager;
import com.jeeplus.modules.resume.service.ResumemanagerService;

/**
 * 简历管理Controller
 * @author xy
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/resume/resumemanager")
public class ResumemanagerController extends BaseController {

	@Autowired
	private ResumemanagerService resumemanagerService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UserMapper userMapper;


	@RequestMapping(value = "form")
	public String editresume(@RequestParam("id") String id,@RequestParam("flag") String flag, Model model) {
		Resumemanager resumemanager=resumemanagerService.get(id);

		model.addAttribute("resumemanager", resumemanager);
		if("change".equals(flag)){
			List<Office> list=officeService.findList(false);
			model.addAttribute("offices",list);
			model.addAttribute("users",userMapper.findList(new User()));
			return "modules/resume/resumeChange";
		}else if("complete".equals(flag)) {
			/*resumemanager.setInterviewer(userMapper.get(resumemanager.getInterviewer()).getName());
			resumemanager.setDeptno(officeService.get(resumemanager.getDeptno()).getName());*/
			model.addAttribute("deptname",officeService.get(resumemanager.getDeptno()).getName());
			model.addAttribute("viewer",userMapper.get(resumemanager.getInterviewer()).getName());
			return "modules/resume/resumeResult";
		}else if("invite".equals(flag)){
			List<Office> list=officeService.findList(false);
			model.addAttribute("offices",list);
			model.addAttribute("users",userMapper.findList(new User()));
			return "modules/resume/resumeInvite";
		}
		return null;
	}


	//查询部门
	@RequestMapping("getOffice")
	@ResponseBody
	public List<Office> getOffice(){
		List<Office> list=officeService.findList(false);
		return list;
	}

	//查询面试官


    /**
     * 简历状态
     * @param id
     * @return
     */

    @RequestMapping("editstatus")
    public String editstatus(@RequestParam String id,@RequestParam String status){
        Resumemanager entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = resumemanagerService.get(id);
        }
        if (entity == null){
            entity = new Resumemanager();
        }
        entity.setStatus(status);
        resumemanagerService.save(entity);
        return "redirect:list";
    }
	
	@ModelAttribute
	public Resumemanager get(@RequestParam(required=false) String id) {
		Resumemanager entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resumemanagerService.get(id);
		}
		if (entity == null){
			entity = new Resumemanager();
		}
		return entity;
	}
	
	/**
	 * 简历管理列表页面
	 */
	@RequiresPermissions("resume:resumemanager:list")
	@RequestMapping(value = {"list", ""})
	public String list(Resumemanager resumemanager, Model model) {
		model.addAttribute("resumemanager", resumemanager);
		return "modules/resume/resumemanagerList";
	}
	
		/**
	 * 简历管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("resume:resumemanager:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Resumemanager resumemanager, HttpServletRequest request, HttpServletResponse response, Model model) {

		System.out.println(resumemanager);
		Page<Resumemanager> page = resumemanagerService.findPage(new Page<Resumemanager>(request, response), resumemanager);
		List<Resumemanager> list=page.getList();
		if(list!=null && list.size()>0) {
			for (Resumemanager resumemanager1 :list) {
				String userid=resumemanager1.getInterviewer();
				User user=userMapper.get(userid);
				String deptno=resumemanager1.getDeptno();
				Office office=officeService.get(deptno);
				resumemanager1.setInterviewer(user!=null?user.getName():"");
				resumemanager1.setDeptno(office!=null?office.getName():"");
			}
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑简历管理表单页面
	 */
	@RequiresPermissions(value={"resume:resumemanager:view","resume:resumemanager:add","resume:resumemanager:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Resumemanager resumemanager, Model model) {
		model.addAttribute("resumemanager", resumemanager);
		model.addAttribute("mode", mode);
		return "modules/resume/resumemanagerForm";
	}

	/**
	 * 保存简历管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"resume:resumemanager:add","resume:resumemanager:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Resumemanager resumemanager, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(resumemanager);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		if(!StringUtils.isEmpty(resumemanager.getFlag())) {
			resumemanager.setStatus(resumemanager.getFlag());
		}
		if(!StringUtils.isEmpty(resumemanager.getResult())){
			if("通过".equals(resumemanager.getResult())){
				resumemanager.setStatus("待入职");
			}else if("未通过".equals(resumemanager.getResult())){
				resumemanager.setStatus("不合适");
			}else if("进入下轮面试".equals(resumemanager.getResult())){
				resumemanager.setStatus("待面试");
			}
		}
		resumemanagerService.save(resumemanager);//保存
		j.setSuccess(true);
		j.setMsg("保存简历管理成功");
		return j;
	}
	
	/**
	 * 删除简历管理
	 */
	@ResponseBody
	@RequiresPermissions("resume:resumemanager:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Resumemanager resumemanager) {
		AjaxJson j = new AjaxJson();
		resumemanagerService.delete(resumemanager);
		j.setMsg("删除简历管理成功");
		return j;
	}
	
	/**
	 * 批量删除简历管理
	 */
	@ResponseBody
	@RequiresPermissions("resume:resumemanager:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resumemanagerService.delete(resumemanagerService.get(id));
		}
		j.setMsg("删除简历管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("resume:resumemanager:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Resumemanager resumemanager, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "简历管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Resumemanager> page = resumemanagerService.findPage(new Page<Resumemanager>(request, response, -1), resumemanager);
    		new ExportExcel("简历管理", Resumemanager.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出简历管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("resume:resumemanager:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Resumemanager> list = ei.getDataList(Resumemanager.class);
			for (Resumemanager resumemanager : list){
				try{
					resumemanagerService.save(resumemanager);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条简历管理记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条简历管理记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入简历管理失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入简历管理数据模板
	 */
	@ResponseBody
	@RequiresPermissions("resume:resumemanager:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "简历管理数据导入模板.xlsx";
    		List<Resumemanager> list = Lists.newArrayList(); 
    		new ExportExcel("简历管理数据", Resumemanager.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
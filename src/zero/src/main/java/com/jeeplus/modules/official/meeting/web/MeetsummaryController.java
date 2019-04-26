/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.meeting.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.modules.annouce.entity.Annouce;
import com.jeeplus.modules.annouce.entity.AnnouceRecord;
import com.jeeplus.modules.official.meeting.entity.MeetsummmaryRecord;
import com.jeeplus.modules.sys.utils.UserUtils;
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
import com.jeeplus.modules.official.meeting.entity.Meetsummary;
import com.jeeplus.modules.official.meeting.service.MeetsummaryService;

/**
 * 会议纪要管理Controller
 * @author xy
 * @version 2019-03-04
 */
@Controller
@RequestMapping(value = "${adminPath}/official/meeting/meetsummary")
public class MeetsummaryController extends BaseController {

	@Autowired
	private MeetsummaryService meetsummaryService;
	
	@ModelAttribute
	public Meetsummary get(@RequestParam(required=false) String id) {
		Meetsummary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = meetsummaryService.get(id);
		}
		if (entity == null){
			entity = new Meetsummary();
		}
		return entity;
	}

	/**
	 * 我的通知列表
	 */
	@RequestMapping(value = "self")
	public String selfList(Annouce annouce, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("isSelf", true);
		return "modules/official/meeting/meetsummaryList";
	}
	
	/**
	 * 会议纪要列表页面
	 */
	@RequiresPermissions("official:meeting:meetsummary:list")
	@RequestMapping(value = {"list", ""})
	public String list(Meetsummary meetsummary, Model model) {
		model.addAttribute("isSelf", false);
		return "modules/official/meeting/meetsummaryList";
	}
	
		/**
	 * 会议纪要列表数据
	 */
	@ResponseBody
	@RequiresPermissions("official:meeting:meetsummary:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Meetsummary meetsummary,boolean isSelf, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(!isSelf) {
			List list = meetsummaryService.findByUserId(UserUtils.getUser().getId());
			if (list != null && list.size() > 0) {
				meetsummary.setInviteperson(UserUtils.getUser().getId());
			} else {
				meetsummary.setInviteperson("");
			}
		}else{
			meetsummary.setInviteperson("");
		}
		meetsummary.setSelf(isSelf);
		Page<Meetsummary> page = meetsummaryService.findPage(new Page<Meetsummary>(request, response), meetsummary);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会议纪要表单页面
	 */
	@RequiresPermissions(value={"official:meeting:meetsummary:view","official:meeting:meetsummary:add","official:meeting:meetsummary:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form( Meetsummary meetsummary,boolean isSelf, Model model) {
		if (StringUtils.isNotBlank(meetsummary.getId())){
			if(isSelf){
				meetsummaryService.updateReadFlag(meetsummary);
				meetsummary = meetsummaryService.get(meetsummary.getId());
			}
			meetsummary = meetsummaryService.getRecordList(meetsummary);
		}
		System.out.println(meetsummary.getStatus());
		model.addAttribute("isSelf", isSelf);
		model.addAttribute("meetsummary", meetsummary);
		return "modules/official/meeting/meetsummaryForm";
	}

	/**
	 * 保存会议纪要
	 */
	@ResponseBody
	@RequiresPermissions(value={"official:meeting:meetsummary:add","official:meeting:meetsummary:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Meetsummary meetsummary, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(meetsummary);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(meetsummary.getId())){
			Meetsummary e = meetsummaryService.get(meetsummary.getId());
			if ("1".equals(e.getStatus())){
				j.setSuccess(false);
				j.setMsg("已发布，不能操作！");
				return j;
			}
		}
		meetsummaryService.save(meetsummary);//保存
		if("1".equals(meetsummary.getStatus())){
			List<MeetsummmaryRecord> list = meetsummary.getMeetsummmaryRecordList();
			for(MeetsummmaryRecord o : list){
				//发送通知到客户端
				ServletContext context = SpringContextHolder
						.getBean(ServletContext.class);
				new SystemInfoSocketHandler().sendMessageToUser(UserUtils.get(o.getUser().getId()).getLoginName(), "收到一条新通知，请到我的通知查看！");
			}
		}
		j.setMsg("保存公告'" + meetsummary.getMeettitle() + "'成功");
		return j;
	}
	
	/**
	 * 删除会议纪要
	 */
	@ResponseBody
	@RequiresPermissions("official:meeting:meetsummary:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Meetsummary meetsummary) {
		AjaxJson j = new AjaxJson();
		meetsummaryService.delete(meetsummary);
		j.setMsg("删除会议纪要成功");
		return j;
	}
	
	/**
	 * 批量删除会议纪要
	 */
	@ResponseBody
	@RequiresPermissions("official:meeting:meetsummary:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			meetsummaryService.delete(meetsummaryService.get(id));
		}
		j.setMsg("删除会议纪要成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("official:meeting:meetsummary:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Meetsummary meetsummary, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议纪要"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Meetsummary> page = meetsummaryService.findPage(new Page<Meetsummary>(request, response, -1), meetsummary);
    		new ExportExcel("会议纪要", Meetsummary.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会议纪要记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("official:meeting:meetsummary:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Meetsummary> list = ei.getDataList(Meetsummary.class);
			for (Meetsummary meetsummary : list){
				try{
					meetsummaryService.save(meetsummary);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会议纪要记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条会议纪要记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入会议纪要失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入会议纪要数据模板
	 */
	@ResponseBody
	@RequiresPermissions("official:meeting:meetsummary:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议纪要数据导入模板.xlsx";
    		List<Meetsummary> list = Lists.newArrayList(); 
    		new ExportExcel("会议纪要数据", Meetsummary.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
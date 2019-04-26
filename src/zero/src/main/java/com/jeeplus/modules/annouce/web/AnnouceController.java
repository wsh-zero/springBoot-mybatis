/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.annouce.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.websocket.service.notify.CommonNotifyService;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.modules.annouce.entity.AnnouceRecord;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.sys.entity.User;
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
import com.jeeplus.modules.annouce.entity.Annouce;
import com.jeeplus.modules.annouce.service.AnnouceService;

/**
 * 发布公告管理Controller
 * @author xy
 * @version 2019-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/annouce/annouce")
public class AnnouceController extends BaseController {

	@Autowired
	private AnnouceService annouceService;

	@Autowired
	private CommonNotifyService commonNotifyService;
	
	@ModelAttribute
	public Annouce get(@RequestParam(required=false) String id) {
		Annouce entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = annouceService.get(id);
		}
		if (entity == null){
			entity = new Annouce();
		}
		return entity;
	}

	/**
	 * 我的通知列表
	 */
	@RequestMapping(value = "self")
	public String selfList(Annouce annouce, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("isSelf", true);
		return "modules/annouce/recvAnnouceList";
	}
	
	/**
	 * 发布公告列表页面
	 */
	@RequiresPermissions("annouce:annouce:list")
	@RequestMapping(value = {"list", ""})
	public String list(Annouce annouce, Model model) {
		model.addAttribute("isSelf", false);
		return "modules/annouce/annouceList";
	}
	
	/**
	 * 发布公告列表数据
	 */
	@ResponseBody
	@RequiresPermissions(value = {"annouce:annouce:list"})
	@RequestMapping(value = "data")
	public Map<String, Object> data(Annouce annouce, boolean isSelf,HttpServletRequest request, HttpServletResponse response, Model model) {
		if(!isSelf) {
			List list = annouceService.findByUserId(UserUtils.getUser().getId());
			if (list != null && list.size() > 0) {
				annouce.setPublishPerson(UserUtils.getUser().getId());
			} else {
				annouce.setPublishPerson("");
			}
		}else{
			annouce.setPublishPerson("");
		}
		annouce.setSelf(isSelf);
		Page<Annouce> page = annouceService.findPage(new Page<Annouce>(request, response), annouce);
		return getBootstrapData(page);
	}

	/**
	 * 接收公告列表数据
	 */
	@ResponseBody
	@RequiresPermissions(value = {"annouce:annouce:list"})
	@RequestMapping(value = "recvdata")
	public Map<String, Object> recvData(Annouce annouce, boolean isSelf,HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<Annouce> page = annouceService.findRecvPage(new Page<Annouce>(request, response), annouce, UserUtils.getUser().getId());
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑发布公告表单页面
	 */
	@RequiresPermissions(value={"annouce:annouce:view","annouce:annouce:add","annouce:annouce:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Annouce annouce,boolean isSelf, Model model) throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(annouce.getId())){
			if(isSelf){
				annouceService.updateReadFlag(annouce);
				annouce = annouceService.get(annouce.getId());
			}
			annouce = annouceService.getRecordList(annouce);
		}
		model.addAttribute("isSelf", isSelf);
		model.addAttribute("annouce", annouce);
		return "modules/annouce/annouceForm";
	}

	/**
	 * 公告详情页面
	 */
	@RequiresPermissions(value={"annouce:annouce:view","annouce:annouce:add","annouce:annouce:edit"},logical=Logical.OR)
	@RequestMapping(value = "annouceinfo")
	public String annouceInfo(Annouce annouce, boolean isSelf, Model model) throws UnsupportedEncodingException {

		annouceService.updateReadFlag(annouce);
		annouce = annouceService.get(annouce.getId());
		model.addAttribute("annouce", annouce);
		return "modules/annouce/annouceInfo";
	}

	/**
	 * 保存发布公告
	 */
	@ResponseBody
	@RequiresPermissions(value={"annouce:annouce:add","annouce:annouce:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Annouce annouce, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		System.out.println(annouce.getContent());
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(annouce);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存

		// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(annouce.getId())){
			Annouce e = annouceService.get(annouce.getId());
			if ("1".equals(e.getStatus())){
				j.setSuccess(false);
				j.setMsg("已发布，不能操作！");
				return j;
			}
		}
		annouceService.save(annouce);//保存
		if("1".equals(annouce.getStatus())){
			List<AnnouceRecord> list = annouce.getAnnouceRecordList();
			for(AnnouceRecord o : list){
				//发送通知到客户端
				ServletContext context = SpringContextHolder
						.getBean(ServletContext.class);
				OaNotify notify = new CommonNotifyService.CommonNotifyBuilder()
						.toUsers(o.getUser().getId())
						.title("收到一条新通知，请到我的通知查看！")
						.content("收到一条公告")
						.handleUrl("/a/annouce/annouce/self")
						.build();

				commonNotifyService.sendNotify(notify);
				// new SystemInfoSocketHandler().sendMessageToUser(UserUtils.get(o.getUser().getId()).getLoginName(), "收到一条新通知，请到我的通知查看！");
			}
		}
		j.setMsg("保存公告'" + annouce.getTitle() + "'成功");
		return j;
	}
	
	/**
	 * 删除发布公告
	 */
	@ResponseBody
	@RequiresPermissions("annouce:annouce:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Annouce annouce) {
		AjaxJson j = new AjaxJson();
		annouceService.delete(annouce);
		j.setMsg("删除发布公告成功");
		return j;
	}
	
	/**
	 * 批量删除发布公告
	 */
	@ResponseBody
	@RequiresPermissions("annouce:annouce:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			annouceService.delete(annouceService.get(id));
		}
		j.setMsg("删除发布公告成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("annouce:annouce:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Annouce annouce, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "发布公告"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Annouce> page = annouceService.findPage(new Page<Annouce>(request, response, -1), annouce);
    		new ExportExcel("发布公告", Annouce.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出发布公告记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("annouce:annouce:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Annouce> list = ei.getDataList(Annouce.class);
			for (Annouce annouce : list){
				try{
					annouceService.save(annouce);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条发布公告记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条发布公告记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入发布公告失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入发布公告数据模板
	 */
	@ResponseBody
	@RequiresPermissions("annouce:annouce:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "发布公告数据导入模板.xlsx";
    		List<Annouce> list = Lists.newArrayList(); 
    		new ExportExcel("发布公告数据", Annouce.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.gallant.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.google.common.collect.Maps;
import com.jeeplus.modules.act.entity.Act;
import com.jeeplus.modules.empee.entity.T_Empee;
import com.jeeplus.modules.gallant.util.AttachmentDTO;
import com.jeeplus.modules.gallant.util.EmailContentUtils;
import com.jeeplus.modules.gallant.util.PageBean;
import com.jeeplus.modules.gallant.util.PraseMimeMsg;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.activiti.engine.repository.ProcessDefinition;
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
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.act.service.ActProcessService;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.gallant.entity.Gallantapplication;
import com.jeeplus.modules.gallant.service.GallantapplicationService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 招骋需求Controller
 * @author xy
 * @version 2019-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/gallant/gallantapplication")
public class GallantapplicationController extends BaseController {

	@Autowired
	private GallantapplicationService gallantapplicationService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;

	@ModelAttribute
	public Gallantapplication get(@RequestParam(required = false) String id) {
		Gallantapplication entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = gallantapplicationService.get(id);
		}
		if (entity == null) {
			entity = new Gallantapplication();
		}
		return entity;
	}


	/**
	 * 查看，增加，编辑招骋需求表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Gallantapplication gallantapplication, Model model) {
		gallantapplication.setCompany(UserUtils.getUser().getCompany().getName());
		gallantapplication.setDepartment(UserUtils.getUser().getOffice().getName());
		model.addAttribute("gallantapplication", gallantapplication);
		if ("add".equals(mode) || "edit".equals(mode)) {
			return "modules/gallant/gallantapplicationForm";
		} else {//audit
			return "modules/gallant/gallantapplicationAudit";
		}
	}

	/**
	 * 保存招骋需求
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(Gallantapplication gallantapplication, Model model) throws Exception {
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(gallantapplication);
		if (StringUtils.isNotBlank(errMsg)) {
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}


		/**
		 * 流程审批
		 */
		if (StringUtils.isBlank(gallantapplication.getId())) {
			//新增或编辑表单保存
			/**
			 * 0:已发布
			 *
			 */
			gallantapplication.setStatus("0");
			gallantapplicationService.save(gallantapplication);//保存
			// 启动流程
			ProcessDefinition p = actProcessService.getProcessDefinition(gallantapplication.getAct().getProcDefId());
			String title = gallantapplication.getCurrentUser().getName() + "在" + DateUtils.getDateTime() + "发起" + p.getName();
			actTaskService.startProcess(p.getKey(), "gallant_application", gallantapplication.getId(), title);
			j.setMsg("发起流程审批成功!");
			j.getBody().put("targetUrl", "/act/task/process/");
		} else {
			//新增或编辑表单保存
			gallantapplication.setStatus("0");
			gallantapplicationService.save(gallantapplication);//保存
			gallantapplication.getAct().setComment(("yes".equals(gallantapplication.getAct().getFlag()) ? "[重新申请] " : "[销毁申请] "));
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("reapply", "yes".equals(gallantapplication.getAct().getFlag()) ? true : false);
			actTaskService.complete(gallantapplication.getAct().getTaskId(), gallantapplication.getAct().getProcInsId(), gallantapplication.getAct().getComment(), gallantapplication.getContent(), vars);
			j.setMsg("提交成功！");
			j.getBody().put("targetUrl", "/act/task/todo/");
		}

		return j;
	}


	@RequiresPermissions("gallant:gallantapplication:list")
	@RequestMapping(value = {"list", ""})
	public String list(Gallantapplication gallantapplication, Model model) {
		model.addAttribute("gallantapplication", gallantapplication);
		return "modules/gallant/gallantapplicationList";
	}

	@ResponseBody
	@RequiresPermissions("gallant:gallantapplication:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Gallantapplication gallantapplication, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Gallantapplication> page = gallantapplicationService.findPage(new Page<Gallantapplication>(request, response), gallantapplication);
		List<Gallantapplication> list = page.getList();
		for (Gallantapplication gallant : list) {
			List<Act> histoicFlowList = actTaskService.histoicFlowList(gallant.getProcInsId(), "", "");
			if (histoicFlowList != null && histoicFlowList.size() == 2) {
				gallant.setOperator(histoicFlowList.get(0).getAssigneeName());
				gallant.setOperatime(histoicFlowList.get(0).getHistIns().getEndTime());
			} else if (histoicFlowList != null && histoicFlowList.size() == 3) {
				gallant.setOperator(histoicFlowList.get(0).getAssigneeName());
				gallant.setOperatime(histoicFlowList.get(0).getHistIns().getEndTime());
				gallant.setGeneral(histoicFlowList.get(1).getAssigneeName());
				gallant.setOperatime(histoicFlowList.get(1).getHistIns().getEndTime());
			} else if (histoicFlowList != null && histoicFlowList.size() == 4) {
				gallant.setOperator(histoicFlowList.get(0).getAssigneeName());
				gallant.setOperatime(histoicFlowList.get(0).getHistIns().getEndTime());
				gallant.setGeneral(histoicFlowList.get(1).getAssigneeName());
				gallant.setGeneraltime(histoicFlowList.get(1).getHistIns().getEndTime());
				gallant.setHrname(histoicFlowList.get(2).getAssigneeName());
				gallant.setHrtime(histoicFlowList.get(2).getHistIns().getEndTime());
				System.out.println(histoicFlowList.get(0).getComment());
				System.out.println(histoicFlowList.get(1).getComment());
				System.out.println(histoicFlowList.get(2).getFlag());
			}
		}


		return getBootstrapData(page);
	}


	/**
	 * 接收邮箱
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getemailcontent")
	public ModelAndView getEmailContent(@RequestParam(name="currpage",defaultValue = "1") Integer currpage) throws Exception {
		PageBean pb=new PageBean();
		pb.setCurrpage(currpage);
		Map<String,String> map= EmailContentUtils.get((currpage-1)*pb.getSizepage(),pb.getSizepage());
		pb.setCountpage(Integer.valueOf(map.get("count")));
		ModelAndView mav = new ModelAndView("modules/gallant/resumeEmail");
		mav.addObject("map", map);
		mav.addObject("pb",pb);
		if(!map.get("count").isEmpty()){
			map.remove("count");
		}
		return mav;
	}


	@RequestMapping("getEmailDetail")
	public void getEmailDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		ServletOutputStream out = response.getOutputStream();
		int msgnum = Integer.parseInt(request.getParameter("msgnum"));
		int bodynum = Integer.parseInt(request.getParameter("bodynum"));
		String filename = request.getParameter("filename");
		Folder folder = (Folder) request.getSession().getAttribute("folder");

		Message msg = folder.getMessage(msgnum + 1);
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		Multipart multi = (Multipart) msg.getContent();
		BodyPart bodyPart = multi.getBodyPart(bodynum);
		InputStream is = bodyPart.getInputStream();
		int c = 0;
		while ((c = is.read()) != -1) {
			out.write(c);
		}
	}


	@RequestMapping("reademail/{msgnum}")
	public ModelAndView reademail(@PathVariable Integer msgnum) throws Exception {
		//Folder folder=EmailContentUtils.folder;
		Folder folder = EmailContentUtils.getFolder();
		//folder.getMessages();
		MimeMessage message = (MimeMessage) folder.getMessages()[msgnum];
		PraseMimeMsg pmm = new PraseMimeMsg(message);
		String subject = pmm.getSubject();
		String from = pmm.getFrom();
		//  to = pmm.getMailAddress("to");
		String sendDate = pmm.getSentDate();
		pmm.getMailContent((Part) message);
		String content = pmm.getBodyText();
		List<AttachmentDTO> attachmentDTOList = pmm.handleMultipart();
		ModelAndView mav=new ModelAndView("modules/gallant/detail");
		mav.addObject("subject",subject);
		mav.addObject("from",from);
		mav.addObject("content",content);
		mav.addObject("attachmentDTOList",attachmentDTOList);
		mav.addObject("msgnum",msgnum);
		mav.addObject("sendDate",sendDate);
		//folder.close(true);
		return mav;
	}

}


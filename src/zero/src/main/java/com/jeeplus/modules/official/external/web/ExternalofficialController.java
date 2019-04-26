/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.external.web;

import java.util.Date;
import java.util.List;
import java.util.Map;


import com.google.common.collect.Maps;
import com.jeeplus.common.taskhandler.processcontroller.ProcessController;
import com.jeeplus.common.taskhandler.processcontroller.ResourceProcessController;
import com.jeeplus.modules.sys.mapper.UserMapper;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.utils.UserUtils;
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
import com.jeeplus.modules.official.external.entity.Externalofficial;
import com.jeeplus.modules.official.external.service.ExternalofficialService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公司对外公文管理Controller
 * @author xy
 * @version 2019-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/official/external/externalofficial")
public class ExternalofficialController extends BaseController {

	@Autowired
	private ExternalofficialService externalofficialService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UserMapper userMapper;

	private ProcessController pc = new ResourceProcessController("act/handlerconf/rules/externalofficial.conf");

	/**
	 * 对外公文列表页面
	 */
	@RequiresPermissions("official:external:externalofficial:list")
	@RequestMapping(value = {"list", ""})
	public String list(Externalofficial externalofficial, Model model) {
		model.addAttribute("externalofficial", externalofficial);
		return "modules/official/external/externalofficialList";
	}

	/**
	 * 对外公文列表数据
	 */
	@ResponseBody
	@RequiresPermissions("official:external:externalofficial:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Externalofficial externalofficial, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Externalofficial> page = externalofficialService.findPage(new Page<Externalofficial>(request, response), externalofficial);
		for(Externalofficial externalofficial1:page.getList()){
			externalofficial1.setPublishdepart(officeService.get(externalofficial1.getPublishdepart()).getName());
			externalofficial1.setPublishperson(userMapper.get(externalofficial1.getPublishperson()).getName());
		}
		return getBootstrapData(page);
	}
	
	@ModelAttribute
	public Externalofficial get(@RequestParam(required=false) String id) {
		Externalofficial entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = externalofficialService.get(id);
		}
		if (entity == null){
			entity = new Externalofficial();
		}
		return entity;
	}
	

	/**
	 * 查看，增加，编辑对外公文表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Externalofficial externalofficial, Model model) {
		model.addAttribute("externalofficial", externalofficial);
		if("add".equals(mode) || "edit".equals(mode)){
			return "modules/official/external/externalofficialForm";
		}else{//audit
			return "modules/official/external/externalofficialAudit";
		}
	}

	/**
	 * 保存对外公文
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(Externalofficial externalofficial, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(externalofficial);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		externalofficial.setPublishperson(UserUtils.getUser().getId());
		externalofficial.setPublishtime(new Date());
		/**
		 * 流程审批
		 */
		if (StringUtils.isBlank(externalofficial.getId())){
			//新增或编辑表单保存
			externalofficialService.save(externalofficial);//保存
			// 启动流程
			ProcessDefinition p = actProcessService.getProcessDefinition(externalofficial.getAct().getProcDefId());
			String title = externalofficial.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(),  "externalofficial", externalofficial.getId(), title);
			j.setMsg("发起流程审批成功!");
			j.getBody().put("targetUrl",  "/act/task/process/");
		}else{
			//新增或编辑表单保存
			externalofficialService.save(externalofficial);//保存
			externalofficial.getAct().setComment(("yes".equals(externalofficial.getAct().getFlag())?"[重新申请] ":"[销毁申请] "));
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("reapply", "yes".equals(externalofficial.getAct().getFlag())? true : false);
			actTaskService.complete(externalofficial.getAct().getTaskId(), externalofficial.getAct().getProcInsId(), externalofficial.getAct().getComment(), externalofficial.getContent(), vars);
			j.setMsg("提交成功！");
			j.getBody().put("targetUrl",  "/act/task/todo/");
		}

		return j;
	}
	


}
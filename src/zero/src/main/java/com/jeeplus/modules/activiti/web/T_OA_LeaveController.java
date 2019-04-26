/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.activiti.web;

import java.util.List;
import java.util.Map;


import com.google.common.collect.Maps;
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
import com.jeeplus.modules.activiti.entity.T_OA_Leave;
import com.jeeplus.modules.activiti.service.T_OA_LeaveService;

/**
 * 请假申请Controller
 * @author xy
 * @version 2019-01-28
 */
@Controller
@RequestMapping(value = "${adminPath}/oativiti/activiti/t_OA_Leave")
public class T_OA_LeaveController extends BaseController {

	@Autowired
	private T_OA_LeaveService t_OA_LeaveService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;
	
	@ModelAttribute
	public T_OA_Leave get(@RequestParam(required=false) String id) {
		T_OA_Leave entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = t_OA_LeaveService.get(id);
		}
		if (entity == null){
			entity = new T_OA_Leave();
		}
		return entity;
	}
	

	/**
	 * 查看，增加，编辑请假申请表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, T_OA_Leave t_OA_Leave, Model model) {
		model.addAttribute("t_OA_Leave", t_OA_Leave);
		if("add".equals(mode) || "edit".equals(mode)){
			return "modules/oativiti/activiti/t_OA_LeaveForm";
		}else{//audit
			return "modules/oativiti/activiti/t_OA_LeaveAudit";
		}
	}

	/**
	 * 保存请假申请
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(T_OA_Leave t_OA_Leave, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(t_OA_Leave);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}


		/**
		 * 流程审批
		 */
		if (StringUtils.isBlank(t_OA_Leave.getId())){
			//新增或编辑表单保存
			t_OA_LeaveService.save(t_OA_Leave);//保存
			// 启动流程
			ProcessDefinition p = actProcessService.getProcessDefinition(t_OA_Leave.getAct().getProcDefId());
			String title = t_OA_Leave.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(),  "t_oaactiviti_leave", t_OA_Leave.getId(), title);
			j.setMsg("发起流程审批成功!");
			j.getBody().put("targetUrl",  "/act/task/process/");
		}else{
			//新增或编辑表单保存
			t_OA_LeaveService.save(t_OA_Leave);//保存
			t_OA_Leave.getAct().setComment(("yes".equals(t_OA_Leave.getAct().getFlag())?"[重新申请] ":"[销毁申请] "));
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("reapply", "yes".equals(t_OA_Leave.getAct().getFlag())? true : false);
			actTaskService.complete(t_OA_Leave.getAct().getTaskId(), t_OA_Leave.getAct().getProcInsId(), t_OA_Leave.getAct().getComment(), t_OA_Leave.getContent(), vars);
			j.setMsg("提交成功！");
			j.getBody().put("targetUrl",  "/act/task/todo/");
		}

		return j;
	}
	


}
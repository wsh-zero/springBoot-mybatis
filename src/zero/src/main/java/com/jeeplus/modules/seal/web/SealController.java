/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.seal.web;

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
import com.jeeplus.modules.seal.entity.Seal;
import com.jeeplus.modules.seal.service.SealService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用印章管理Controller
 * @author xy
 * @version 2019-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/seal/seal")
public class SealController extends BaseController {

	@Autowired
	private SealService sealService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;
	
	@ModelAttribute
	public Seal get(@RequestParam(required=false) String id) {
		Seal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sealService.get(id);
		}
		if (entity == null){
			entity = new Seal();
		}
		return entity;
	}

	/**
	 * 用印管理列表页面
	 */
	@RequiresPermissions("seal:seal:list")
	@RequestMapping(value = {"list", ""})
	public String list(Seal seal, Model model) {
		model.addAttribute("seal", seal);
		return "modules/seal/sealList";
	}

	/**
	 * 用印管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("seal:seal:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Seal seal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Seal> page = sealService.findPage(new Page<Seal>(request, response), seal);
		return getBootstrapData(page);
	}
	

	/**
	 * 查看，增加，编辑用印管理表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Seal seal, Model model) {
		model.addAttribute("seal", seal);
		if("add".equals(mode) || "edit".equals(mode)){
			return "modules/seal/sealForm";
		}else{//audit
			return "modules/seal/sealAudit";
		}
	}

	/**
	 * 保存用印管理
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(Seal seal, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(seal);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}


		/**
		 * 流程审批
		 */
		if (StringUtils.isBlank(seal.getId())){
			//新增或编辑表单保存
			sealService.save(seal);//保存
			// 启动流程
			ProcessDefinition p = actProcessService.getProcessDefinition(seal.getAct().getProcDefId());
			String title = seal.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(),  "seal", seal.getId(), title);
			j.setMsg("发起流程审批成功!");
			j.getBody().put("targetUrl",  "/act/task/process/");
		}else{
			//新增或编辑表单保存
			sealService.save(seal);//保存
			seal.getAct().setComment(("yes".equals(seal.getAct().getFlag())?"[重新申请] ":"[销毁申请] "));
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("reapply", "yes".equals(seal.getAct().getFlag())? true : false);
			actTaskService.complete(seal.getAct().getTaskId(), seal.getAct().getProcInsId(), seal.getAct().getComment(), seal.getContent(), vars);
			j.setMsg("提交成功！");
			j.getBody().put("targetUrl",  "/act/task/todo/");
		}

		return j;
	}
	


}
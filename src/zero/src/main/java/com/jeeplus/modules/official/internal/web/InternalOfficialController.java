/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.official.internal.web;

import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.taskhandler.handler.step.SendNotifyHandler;
import com.jeeplus.common.taskhandler.processcontroller.ProcessController;
import com.jeeplus.common.taskhandler.processcontroller.ResourceProcessController;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.act.service.ActProcessService;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.official.internal.entity.InternalOfficial;
import com.jeeplus.modules.official.internal.service.InternalOfficialService;
import com.jeeplus.modules.official.internalrecord.service.InternalOfficalRecordService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 公司对内公文管理Controller
 * @author chentao
 * @version 2019-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/official/internal/internalOfficial")
public class InternalOfficialController extends BaseController {

	@Autowired
	private InternalOfficialService internalOfficialService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
    private InternalOfficalRecordService recordService;

	private ProcessController pc =new ResourceProcessController("act/handlerconf/rules/internalofficial.conf");
	
	@ModelAttribute
	public InternalOfficial get(@RequestParam(required=false) String id) {
		InternalOfficial entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = internalOfficialService.get(id);
            // InternalOfficalRecord record = new InternalOfficalRecord();
            // record.setOfficalId(entity.getId());
			// entity.setTargets(recordService.findList(record));
		}
		if (entity == null){
			entity = new InternalOfficial();
		}
		return entity;
	}
	

	/**
	 * 列表页面
	 */
	@RequestMapping(value = "")
	public String form(InternalOfficial internalOfficial, boolean isSelf, Model model) {
		if (isSelf) {
			model.addAttribute("isSelf", true);
		}
		model.addAttribute("internalofficial", new InternalOfficial());
	    return "modules/official/internal/internalofficialList";
	}

    /**
     * 查看，增加，编辑对内公文表单页面
     */
    @RequestMapping(value = "form/{mode}")
    public String list(@PathVariable String mode, InternalOfficial internalOfficial, Model model) {
        model.addAttribute("internalOfficial", internalOfficial);
        if("add".equals(mode) || "edit".equals(mode)){
            return "modules/official/internal/internalofficialForm";
        } else if ("view".equals(mode)) {
        	model.addAttribute("view", "view");
			return "modules/official/internal/internalofficialAudit";
		} else if ("viewSelf".equals(mode)) {
			recordService.updateReaded(internalOfficial.getId(), UserUtils.getUser().getId(), "1");
			return "modules/official/internal/internalofficialAudit";
		} else {//audit
            return "modules/official/internal/internalofficialAudit";
        }
    }
    /**
     * 公文列表数据
     */
    @ResponseBody
    @RequiresPermissions(value = {"official:internal:internalofficial:list"})
    @RequestMapping(value = "data")
    public Map<String, Object> data(InternalOfficial internalOfficial,boolean isSelf, HttpServletRequest request, HttpServletResponse response, Model model) {
    	if (isSelf == true) {
    		model.addAttribute("isSelf", true);
    		internalOfficial.setIsSelf(true);
    		// internalOfficial.setCurrentUser(UserUtils.getUser());
		}
        Page<InternalOfficial> page = internalOfficialService.findPage(new Page<InternalOfficial>(request, response), internalOfficial);
        return getBootstrapData(page);
    }

	/**
	 * 保存对内公文
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(InternalOfficial internalOfficial, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(internalOfficial);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}


		/**
		 * 流程审批
		 */
		if (StringUtils.isBlank(internalOfficial.getId())){
			//新增或编辑表单保存
			internalOfficial.setPublishperson(UserUtils.getUser().getId());
			internalOfficial.setPublishtime(new Date());
			internalOfficial.setPublishdepart(UserUtils.getUser().getOffice().getId());
			internalOfficialService.save(internalOfficial);//保存
			// 启动流程
			ProcessDefinition p = actProcessService.getProcessDefinition(internalOfficial.getAct().getProcDefId());
			String title = internalOfficial.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(),  "internalofficial", internalOfficial.getId(), title, pc.execute(internalOfficial));
			j.setMsg("发起流程审批成功!");
			j.getBody().put("targetUrl",  "/act/task/process/");
		}else{
			//新增或编辑表单保存
			internalOfficialService.save(internalOfficial);//保存
			internalOfficial.getAct().setComment(("yes".equals(internalOfficial.getAct().getFlag())?"[重新申请] ":"[销毁申请] "));
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("reapply", "yes".equals(internalOfficial.getAct().getFlag())? true : false);
			actTaskService.complete(internalOfficial.getAct().getTaskId(), internalOfficial.getAct().getProcInsId(), internalOfficial.getAct().getComment(), internalOfficial.getContent(), pc.execute(internalOfficial, vars));
			j.setMsg("提交成功！");
			j.getBody().put("targetUrl",  "/act/task/todo/");
		}

		return j;
	}
}
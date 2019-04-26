/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.config.Global;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.personnel.plan.entity.Org;
import com.jeeplus.modules.personnel.plan.service.OrgService;

/**
 * 组织Controller
 * @author 王伟
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/plan/org")
public class OrgController extends BaseController {

	@Autowired
	private OrgService orgService;
	
	@ModelAttribute
	public Org get(@RequestParam(required=false) String id) {
		Org entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orgService.get(id);
		}
		if (entity == null){
			entity = new Org();
		}
		return entity;
	}
	
	/**
	 * 组织列表页面
	 */
	@RequiresPermissions("personnel:plan:org:list")
	@RequestMapping(value = {"list", ""})
	public String list(Org org,  HttpServletRequest request, HttpServletResponse response, Model model) {
	
		model.addAttribute("org", org);
		return "modules/personnel/plan/orgList";
	}

	/**
	 * 查看，增加，编辑组织表单页面
	 */
	@RequiresPermissions(value={"personnel:plan:org:view","personnel:plan:org:add","personnel:plan:org:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Org org, Model model) {
		if (org.getParent()!=null && StringUtils.isNotBlank(org.getParent().getId())){
			org.setParent(orgService.get(org.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(org.getId())){
				Org orgChild = new Org();
				orgChild.setParent(new Org(org.getParent().getId()));
				List<Org> list = orgService.findList(org); 
				if (list.size() > 0){
					org.setSort(list.get(list.size()-1).getSort());
					if (org.getSort() != null){
						org.setSort(org.getSort() + 30);
					}
				}
			}
		}
		if (org.getSort() == null){
			org.setSort(30);
		}
		model.addAttribute("org", org);
		return "modules/personnel/plan/orgForm";
	}

	/**
	 * 保存组织
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:plan:org:add","personnel:plan:org:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Org org, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(org);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存

			j = orgService.preserve(org);
			return j;

		//保存

	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<Org> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return orgService.getChildren(parentId);
	}
	
	/**
	 * 删除组织
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:org:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Org org) {
		AjaxJson j = new AjaxJson();
		orgService.delete(org);
		j.setSuccess(true);
		j.setMsg("删除组织成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Org> list = orgService.findList(new Org());
		for (int i=0; i<list.size(); i++){
			Org e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}
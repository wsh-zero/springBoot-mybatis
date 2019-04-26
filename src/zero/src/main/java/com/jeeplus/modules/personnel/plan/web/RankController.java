/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;
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
import com.jeeplus.modules.personnel.plan.entity.Rank;
import com.jeeplus.modules.personnel.plan.service.RankService;

/**
 * 职级管理Controller
 * @author 王伟
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/plan/rank")
public class RankController extends BaseController {

	@Autowired
	private RankService rankService;

	@Autowired
	private StaffService staffService;

	@ModelAttribute
	public Rank get(@RequestParam(required=false) String id) {
		Rank entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rankService.get(id);
		}
		if (entity == null){
			entity = new Rank();
		}
		return entity;
	}

	/**
	 * 职级列表页面
	 */
	@RequiresPermissions("personnel:plan:rank:list")
	@RequestMapping(value = {"list", ""})
	public String list(Rank rank, Model model) {
		model.addAttribute("rank", rank);
		return "modules/personnel/plan/rankList";
	}

	/**
	 * 职级列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:rank:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Rank rank, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Rank> page = rankService.findPage(new Page<Rank>(request, response), rank);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑职级表单页面
	 */
	@RequiresPermissions(value={"personnel:plan:rank:view","personnel:plan:rank:add","personnel:plan:rank:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Rank rank, Model model) {
		model.addAttribute("rank", rank);
		return "modules/personnel/plan/rankForm";
	}

	/**
	 * 保存职级
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:plan:rank:add","personnel:plan:rank:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Rank rank, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(rank);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		j = rankService.preserve(rank);//保存
		return j;
	}

	/**
	 * 删除职级
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:rank:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Rank rank) {
		AjaxJson j = new AjaxJson();
		Staff staff =new Staff();
		staff.setRank(rank);
		int count = staffService.count(staff);
		if (count > 0 ){
			j.setSuccess(false);
			j.setMsg("当前职级使用中，无法删除");
			return j;
		}
		rankService.delete(rank);
		j.setMsg("删除职级成功");
		return j;
	}

	/**
	 * 批量删除职级
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:rank:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			Rank rank = new Rank();
			rank.setId(id);
			Staff staff =new Staff();
			staff.setRank(rank);
			int count = staffService.count(staff);
			if (count > 0 ){
				Rank r = rankService.get(id);
				j.setSuccess(false);
				j.setMsg("当前职级"+r.getRankName()+"使用中，无法删除");
				return j;
			}
			rankService.delete(rankService.get(id));
		}
		j.setMsg("删除职级成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:rank:export")
	@RequestMapping(value = "export")
	public AjaxJson exportFile(Rank rank, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "职级"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Rank> page = rankService.findPage(new Page<Rank>(request, response, -1), rank);
			new ExportExcel("职级", Rank.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出职级记录失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:rank:import")
	@RequestMapping(value = "import")
	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Rank> list = ei.getDataList(Rank.class);
			for (Rank rank : list){
				try{
					rankService.save(rank);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条职级记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条职级记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入职级失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 下载导入职级数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:rank:import")
	@RequestMapping(value = "import/template")
	public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "职级数据导入模板.xlsx";
			List<Rank> list = Lists.newArrayList();
			new ExportExcel("职级数据", Rank.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
	}

}
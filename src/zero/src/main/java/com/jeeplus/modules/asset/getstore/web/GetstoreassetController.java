/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.asset.getstore.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
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
import com.jeeplus.modules.asset.getstore.entity.Getstoreasset;
import com.jeeplus.modules.asset.getstore.service.GetstoreassetService;

/**
 * 固定资产出库管理Controller
 * @author xy
 * @version 2019-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/asset/getstore/getstoreasset")
public class GetstoreassetController extends BaseController {

	@Autowired
	private GetstoreassetService getstoreassetService;

	@Autowired
	private UserMapper userMapper;
	
	@ModelAttribute
	public Getstoreasset get(@RequestParam(required=false) String id) {
		Getstoreasset entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = getstoreassetService.get(id);
		}
		if (entity == null){
			entity = new Getstoreasset();
		}
		return entity;
	}
	
	/**
	 * 资产出库列表页面
	 */
	@RequiresPermissions("asset:getstore:getstoreasset:list")
	@RequestMapping(value = {"list", ""})
	public String list(Getstoreasset getstoreasset, Model model) {
		model.addAttribute("getstoreasset", getstoreasset);
		return "modules/asset/getstore/getstoreassetList";
	}
	
		/**
	 * 资产出库列表数据
	 */
	@ResponseBody
	@RequiresPermissions("asset:getstore:getstoreasset:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Getstoreasset getstoreasset, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Getstoreasset> page = getstoreassetService.findPage(new Page<Getstoreasset>(request, response), getstoreasset);
		for(Getstoreasset getstoreasset1:page.getList()){
			getstoreasset1.setUserpersonlist(getstoreassetService.getStoreList(getstoreasset1.getUseperson()));
			getstoreasset1.setUseperson(getstoreasset1.getUserpersonNames());
			User u=userMapper.get(getstoreasset1.getPersonliable());
			getstoreasset1.setPersonliable(u.getName());
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资产出库表单页面
	 */
	@RequiresPermissions(value={"asset:getstore:getstoreasset:view","asset:getstore:getstoreasset:add","asset:getstore:getstoreasset:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Getstoreasset getstoreasset, Model model) {
		if(!StringUtils.isEmpty(getstoreasset.getUseperson()))
		getstoreasset.setUserpersonlist(getstoreassetService.getStoreList(getstoreasset.getUseperson()));
		if(!StringUtils.isEmpty(getstoreasset.getPersonliable()))
		getstoreasset.setPsersonlianame(userMapper.get(getstoreasset.getPersonliable()).getName());
		model.addAttribute("getstoreasset", getstoreasset);
		model.addAttribute("mode", mode);
		return "modules/asset/getstore/getstoreassetForm";
	}

	/**
	 * 保存资产出库
	 */
	@ResponseBody
	@RequiresPermissions(value={"asset:getstore:getstoreasset:add","asset:getstore:getstoreasset:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Getstoreasset getstoreasset, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(getstoreasset);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		getstoreassetService.save(getstoreasset);//保存
		j.setSuccess(true);
		j.setMsg("保存资产出库成功");
		return j;
	}
	
	/**
	 * 删除资产出库
	 */
	@ResponseBody
	@RequiresPermissions("asset:getstore:getstoreasset:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Getstoreasset getstoreasset) {
		AjaxJson j = new AjaxJson();
		getstoreassetService.delete(getstoreasset);
		j.setMsg("删除资产出库成功");
		return j;
	}
	
	/**
	 * 批量删除资产出库
	 */
	@ResponseBody
	@RequiresPermissions("asset:getstore:getstoreasset:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			getstoreassetService.delete(getstoreassetService.get(id));
		}
		j.setMsg("删除资产出库成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("asset:getstore:getstoreasset:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Getstoreasset getstoreasset, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资产出库"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Getstoreasset> page = getstoreassetService.findPage(new Page<Getstoreasset>(request, response, -1), getstoreasset);
    		new ExportExcel("资产出库", Getstoreasset.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资产出库记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("asset:getstore:getstoreasset:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Getstoreasset> list = ei.getDataList(Getstoreasset.class);
			for (Getstoreasset getstoreasset : list){
				try{
					getstoreassetService.save(getstoreasset);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资产出库记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条资产出库记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入资产出库失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入资产出库数据模板
	 */
	@ResponseBody
	@RequiresPermissions("asset:getstore:getstoreasset:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资产出库数据导入模板.xlsx";
    		List<Getstoreasset> list = Lists.newArrayList(); 
    		new ExportExcel("资产出库数据", Getstoreasset.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
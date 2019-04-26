/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.asset.putstore.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.asset.putstore.entity.Putstoreasset;
import com.jeeplus.modules.asset.putstore.service.PutstoreassetService;

/**
 * 固定资产入库管理Controller
 * @author xy
 * @version 2019-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/asset/putstore/putstoreasset")
public class PutstoreassetController extends BaseController {

	@Autowired
	private PutstoreassetService putstoreassetService;
	
	@ModelAttribute
	public Putstoreasset get(@RequestParam(required=false) String id) {
		Putstoreasset entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = putstoreassetService.get(id);
		}
		if (entity == null){
			entity = new Putstoreasset();
		}
		return entity;
	}
	
	/**
	 * 资产入库列表页面
	 */
	@RequiresPermissions("asset:putstore:putstoreasset:list")
	@RequestMapping(value = {"list", ""})
	public String list(Putstoreasset putstoreasset, Model model) {
		model.addAttribute("putstoreasset", putstoreasset);
		return "modules/asset/putstore/putstoreassetList";
	}
	
		/**
	 * 资产入库列表数据
	 */
	@ResponseBody
	@RequiresPermissions("asset:putstore:putstoreasset:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Putstoreasset putstoreasset, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Putstoreasset> page = putstoreassetService.findPage(new Page<Putstoreasset>(request, response), putstoreasset); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资产入库表单页面
	 */
	@RequiresPermissions(value={"asset:putstore:putstoreasset:view","asset:putstore:putstoreasset:add","asset:putstore:putstoreasset:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Putstoreasset putstoreasset, Model model) {
		model.addAttribute("putstoreasset", putstoreasset);
		model.addAttribute("mode", mode);
		return "modules/asset/putstore/putstoreassetForm";
	}

	/**
	 * 保存资产入库
	 */
	@ResponseBody
	@RequiresPermissions(value={"asset:putstore:putstoreasset:add","asset:putstore:putstoreasset:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Putstoreasset putstoreasset, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(putstoreasset);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		putstoreassetService.save(putstoreasset);//保存
		j.setSuccess(true);
		j.setMsg("保存资产入库成功");
		return j;
	}
	
	/**
	 * 删除资产入库
	 */
	@ResponseBody
	@RequiresPermissions("asset:putstore:putstoreasset:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Putstoreasset putstoreasset) {
		AjaxJson j = new AjaxJson();
		putstoreassetService.delete(putstoreasset);
		j.setMsg("删除资产入库成功");
		return j;
	}
	
	/**
	 * 批量删除资产入库
	 */
	@ResponseBody
	@RequiresPermissions("asset:putstore:putstoreasset:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			putstoreassetService.delete(putstoreassetService.get(id));
		}
		j.setMsg("删除资产入库成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("asset:putstore:putstoreasset:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Putstoreasset putstoreasset, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资产入库"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Putstoreasset> page = putstoreassetService.findPage(new Page<Putstoreasset>(request, response, -1), putstoreasset);
    		new ExportExcel("资产入库", Putstoreasset.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资产入库记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("asset:putstore:putstoreasset:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Putstoreasset> list = ei.getDataList(Putstoreasset.class);
			for (Putstoreasset putstoreasset : list){
				try{
					putstoreassetService.save(putstoreasset);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资产入库记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条资产入库记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入资产入库失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入资产入库数据模板
	 */
	@ResponseBody
	@RequiresPermissions("asset:putstore:putstoreasset:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资产入库数据导入模板.xlsx";
    		List<Putstoreasset> list = Lists.newArrayList(); 
    		new ExportExcel("资产入库数据", Putstoreasset.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
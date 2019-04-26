/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.aptitude.cooper.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.aptitude.util.NumberUtils;
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
import com.jeeplus.modules.aptitude.cooper.entity.Cooperaptitude;
import com.jeeplus.modules.aptitude.cooper.service.CooperaptitudeService;

/**
 * 合作个人资质管理Controller
 * @author xy
 * @version 2019-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/aptitude/cooper/cooperaptitude")
public class CooperaptitudeController extends BaseController {

	@Autowired
	private CooperaptitudeService cooperaptitudeService;
	
	@ModelAttribute
	public Cooperaptitude get(@RequestParam(required=false) String id) {
		Cooperaptitude entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cooperaptitudeService.get(id);
		}
		if (entity == null){
			entity = new Cooperaptitude();
		}
		return entity;
	}
	
	/**
	 * 合作个人资质列表页面
	 */
	@RequiresPermissions("aptitude:cooper:cooperaptitude:list")
	@RequestMapping(value = {"list", ""})
	public String list(Cooperaptitude cooperaptitude, Model model) {
		model.addAttribute("cooperaptitude", cooperaptitude);
		return "modules/aptitude/cooper/cooperaptitudeList";
	}
	
		/**
	 * 合作个人资质列表数据
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:cooper:cooperaptitude:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Cooperaptitude cooperaptitude, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Cooperaptitude> page = cooperaptitudeService.findPage(new Page<Cooperaptitude>(request, response), cooperaptitude);

		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑合作个人资质表单页面
	 */
	@RequiresPermissions(value={"aptitude:cooper:cooperaptitude:view","aptitude:cooper:cooperaptitude:add","aptitude:cooper:cooperaptitude:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Cooperaptitude cooperaptitude, Model model) {
		model.addAttribute("cooperaptitude", cooperaptitude);
		model.addAttribute("mode", mode);
		return "modules/aptitude/cooper/cooperaptitudeForm";
	}

	/**
	 * 保存合作个人资质
	 */
	@ResponseBody
	@RequiresPermissions(value={"aptitude:cooper:cooperaptitude:add","aptitude:cooper:cooperaptitude:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Cooperaptitude cooperaptitude, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		boolean flag= NumberUtils.isPureDigital(cooperaptitude.getValidtime()+"");
		if(flag==false){
			j.setSuccess(false);
			j.setMsg("有效期限只能为整数");
			return j;
		}
		String errMsg = beanValidator(cooperaptitude);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		cooperaptitudeService.save(cooperaptitude);//保存
		j.setSuccess(true);
		j.setMsg("保存合作个人资质成功");
		return j;
	}
	
	/**
	 * 删除合作个人资质
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:cooper:cooperaptitude:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Cooperaptitude cooperaptitude) {
		AjaxJson j = new AjaxJson();
		cooperaptitudeService.delete(cooperaptitude);
		j.setMsg("删除合作个人资质成功");
		return j;
	}
	
	/**
	 * 批量删除合作个人资质
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:cooper:cooperaptitude:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cooperaptitudeService.delete(cooperaptitudeService.get(id));
		}
		j.setMsg("删除合作个人资质成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:cooper:cooperaptitude:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Cooperaptitude cooperaptitude, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "合作个人资质"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Cooperaptitude> page = cooperaptitudeService.findPage(new Page<Cooperaptitude>(request, response, -1), cooperaptitude);
    		new ExportExcel("合作个人资质", Cooperaptitude.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出合作个人资质记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("aptitude:cooper:cooperaptitude:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Cooperaptitude> list = ei.getDataList(Cooperaptitude.class);
			for (Cooperaptitude cooperaptitude : list){
				try{
					cooperaptitudeService.save(cooperaptitude);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条合作个人资质记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条合作个人资质记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入合作个人资质失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入合作个人资质数据模板
	 */
	@ResponseBody
	@RequiresPermissions("aptitude:cooper:cooperaptitude:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "合作个人资质数据导入模板.xlsx";
    		List<Cooperaptitude> list = Lists.newArrayList(); 
    		new ExportExcel("合作个人资质数据", Cooperaptitude.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.achieve.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.personnel.achieve.entity.PerConf;
import com.jeeplus.modules.personnel.achieve.service.PerConfService;

/**
 * 绩效配置Controller
 * @author ww
 * @version 2019-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/achieve/perConf")
public class PerConfController extends BaseController {

	@Autowired
	private PerConfService perConfService;
	@Autowired
	private UserMapper userMapper;

	@ModelAttribute
	public PerConf get(@RequestParam(required = false) String id) {
		PerConf entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = perConfService.get(id);
		}
		if (entity == null) {
			entity = new PerConf();
		}
		return entity;
	}

	/**
	 * 绩效配置列表页面
	 */
	@RequiresPermissions("personnel:achieve:perConf:list")
	@RequestMapping(value = {"list", ""})
	public String list(PerConf perConf, Model model) {
		model.addAttribute("perConf", perConf);
		return "modules/personnel/achieve/perConfList";
	}

	/**
	 * 绩效配置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:perConf:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PerConf perConf, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PerConf> page = perConfService.findPage(new Page<PerConf>(request, response), perConf);
		for(PerConf per : page.getList()){
			per.setCreateBy(userMapper.get(per.getCreateBy().getId()));
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑绩效配置表单页面
	 */
	@RequiresPermissions(value = {"personnel:achieve:perConf:view", "personnel:achieve:perConf:add", "personnel:achieve:perConf:edit"}, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(PerConf perConf, Model model) {
		model.addAttribute("perConf", perConf);
		return "modules/personnel/achieve/perConfForm";
	}

	/**
	 * 保存绩效配置
	 */
	@ResponseBody
	@RequiresPermissions(value = {"personnel:achieve:perConf:add", "personnel:achieve:perConf:edit"}, logical = Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PerConf perConf, Model model) throws Exception {
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(perConf);
		if (StringUtils.isNotBlank(errMsg)) {
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		try {
			PerConf per = perConfService.find(perConf.getName());
			if (per != null) {
				j.setMsg("考核标准重复");
				j.setSuccess(false);
				return j;
			}
			//新增或编辑表单保存

		} catch (Exception e) {
			e.printStackTrace();
		}
		perConfService.save(perConf);//保存
		j.setSuccess(true);
		j.setMsg("保存绩效配置成功");
		return j;
	}


	
	/**
	 * 删除绩效配置
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:perConf:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PerConf perConf) {
		AjaxJson j = new AjaxJson();
		perConfService.delete(perConf);
		j.setMsg("删除绩效配置成功");
		return j;
	}
	
	/**
	 * 批量删除绩效配置
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:perConf:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			perConfService.delete(perConfService.get(id));
		}
		j.setMsg("删除绩效配置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:perConf:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(PerConf perConf, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "绩效配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PerConf> page = perConfService.findPage(new Page<PerConf>(request, response, -1), perConf);
    		new ExportExcel("绩效配置", PerConf.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出绩效配置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:perConf:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PerConf> list = ei.getDataList(PerConf.class);
			for (PerConf perConf : list){
				try{
					perConfService.save(perConf);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条绩效配置记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条绩效配置记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入绩效配置失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入绩效配置数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:perConf:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "绩效配置数据导入模板.xlsx";
    		List<PerConf> list = Lists.newArrayList(); 
    		new ExportExcel("绩效配置数据", PerConf.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
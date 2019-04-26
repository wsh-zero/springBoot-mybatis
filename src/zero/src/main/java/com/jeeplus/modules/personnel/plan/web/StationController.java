/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.manager.util.NumberUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
import com.jeeplus.modules.personnel.plan.entity.Station;
import com.jeeplus.modules.personnel.plan.service.StationService;

/**
 * 岗位管理Controller
 * @author 王伟
 * @version 2019-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/plan/station")
public class StationController extends BaseController {

	@Autowired
	private StationService stationService;
	@Autowired
	private NumberUtil numberUtil;

	@ModelAttribute
	public Station get(@RequestParam(required=false) String id) {
		Station entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = stationService.get(id);
		}
		if (entity == null){
			entity = new Station();
		}
		return entity;
	}

	/**
	 * 岗位管理列表页面
	 */
	@RequiresPermissions("personnel:plan:station:list")
	@RequestMapping(value = {"list", ""})
	public String list(Station station, Model model) {
		model.addAttribute("station", station);
		return "modules/personnel/plan/stationList";
	}

	/**
	 * 岗位管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:station:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Station station, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Station> page = stationService.findPage(new Page<Station>(request, response), station);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑岗位管理表单页面
	 */
	@RequiresPermissions(value={"personnel:plan:station:view","personnel:plan:station:add","personnel:plan:station:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode  ,Station station, Model model) {
		if ("add1".equals(mode)){
			Integer currcode = stationService.getMaxNumber();
			String maxnum = "";
			if(currcode==null || currcode<=0){
				maxnum = "0001";
			}else {
				DecimalFormat countFormat = new DecimalFormat("0000");
				maxnum = countFormat.format(currcode + 1);
			}
			station.setGradeNumber(numberUtil.STATIONCODE + maxnum);   //岗位编号
			station.setNumber(maxnum);
		}
		model.addAttribute("station", station);
		model.addAttribute("mode",mode);
		return "modules/personnel/plan/stationForm";
	}

	/**
	 * 保存岗位管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:plan:station:add","personnel:plan:station:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Station station, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(station);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存

		j =stationService.preserve(station);//保存

		return j;
	}

	/**
	 * 删除岗位管理
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:station:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Station station) {
		AjaxJson j = new AjaxJson();
		stationService.delete(station);
		j.setMsg("删除岗位管理成功");
		return j;
	}

	/**
	 * 批量删除岗位管理
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:station:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			stationService.delete(stationService.get(id));
		}
		j.setMsg("删除岗位管理成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:station:export")
	@RequestMapping(value = "export")
	public AjaxJson exportFile(Station station, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "岗位管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<Station> page = stationService.findPage(new Page<Station>(request, response, -1), station);
			new ExportExcel("岗位管理", Station.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出岗位管理记录失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:station:import")
	@RequestMapping(value = "import")
	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Station> list = ei.getDataList(Station.class);
			for (Station station : list){
				try{
					stationService.save(station);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条岗位管理记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条岗位管理记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入岗位管理失败！失败信息："+e.getMessage());
		}
		return j;
	}

	/**
	 * 下载导入岗位管理数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:plan:station:import")
	@RequestMapping(value = "import/template")
	public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "岗位管理数据导入模板.xlsx";
			List<Station> list = Lists.newArrayList();
			new ExportExcel("岗位管理数据", Station.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
	}

}
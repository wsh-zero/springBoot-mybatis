/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.train.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.train.entity.Train;
import com.jeeplus.modules.personnel.train.service.TrainService;
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
import com.jeeplus.modules.personnel.train.entity.TrainType;
import com.jeeplus.modules.personnel.train.service.TrainTypeService;

/**
 * 培训类型Controller
 * @author 王伟
 * @version 2019-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/train/trainType")
public class TrainTypeController extends BaseController {

	@Autowired
	private TrainTypeService trainTypeService;
	@Autowired
	private TrainService trainService;
	
	@ModelAttribute
	public TrainType get(@RequestParam(required=false) String id) {
		TrainType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = trainTypeService.get(id);
		}
		if (entity == null){
			entity = new TrainType();
		}
		return entity;
	}
	
	/**
	 * 培训类型列表页面
	 */
	@RequiresPermissions("personnel:train:trainType:list")
	@RequestMapping(value = {"list", ""})
	public String list(TrainType trainType, Model model) {
		model.addAttribute("trainType", trainType);
		return "modules/personnel/train/trainTypeList";
	}
	
		/**
	 * 培训类型列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:trainType:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TrainType trainType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TrainType> page = trainTypeService.findPage(new Page<TrainType>(request, response), trainType); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑培训类型表单页面
	 */
	@RequiresPermissions(value={"personnel:train:trainType:view","personnel:train:trainType:add","personnel:train:trainType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TrainType trainType, Model model) {
		model.addAttribute("trainType", trainType);
		return "modules/personnel/train/trainTypeForm";
	}

	/**
	 * 保存培训类型
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:train:trainType:add","personnel:train:trainType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TrainType trainType, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(trainType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		trainTypeService.save(trainType);//保存
		j.setSuccess(true);
		j.setMsg("保存培训类型成功");
		return j;
	}
	
	/**
	 * 删除培训类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:trainType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TrainType trainType) {
		AjaxJson j = new AjaxJson();
		trainTypeService.delete(trainType);
		j.setMsg("删除培训类型成功");
		return j;
	}
	
	/**
	 * 批量删除培训类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:trainType:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			TrainType trainType = new TrainType();
			trainType.setId(id);
			TrainType type = trainTypeService.get(id);
			Train train = new Train();
			train.setType(type);
			int count = trainService.count(train);
			if (count >0){
				j.setSuccess(false);
				j.setMsg("当前类型使用中，无法删除");
				return j;
			}



			trainTypeService.delete(trainTypeService.get(id));
		}
		j.setMsg("删除培训类型成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:trainType:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TrainType trainType, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "培训类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TrainType> page = trainTypeService.findPage(new Page<TrainType>(request, response, -1), trainType);
    		new ExportExcel("培训类型", TrainType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出培训类型记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:trainType:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TrainType> list = ei.getDataList(TrainType.class);
			for (TrainType trainType : list){
				try{
					trainTypeService.save(trainType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条培训类型记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条培训类型记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入培训类型失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入培训类型数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:trainType:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "培训类型数据导入模板.xlsx";
    		List<TrainType> list = Lists.newArrayList(); 
    		new ExportExcel("培训类型数据", TrainType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
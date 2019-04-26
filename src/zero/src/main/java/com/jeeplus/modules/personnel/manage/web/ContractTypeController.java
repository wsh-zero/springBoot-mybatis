/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manage.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.manager.entity.Contract;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.ContractService;
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
import com.jeeplus.modules.personnel.manage.entity.ContractType;
import com.jeeplus.modules.personnel.manage.service.ContractTypeService;

/**
 * 合同类型Controller
 * @author 王伟
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/manage/contractType")
public class ContractTypeController extends BaseController {

	@Autowired
	private ContractTypeService contractTypeService;

	@Autowired
	private StaffService staffService;
	@Autowired
	private ContractService contractService;
	
	@ModelAttribute
	public ContractType get(@RequestParam(required=false) String id) {
		ContractType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractTypeService.get(id);
		}
		if (entity == null){
			entity = new ContractType();
		}
		return entity;
	}
	
	/**
	 * 合同类型列表页面
	 */
	@RequiresPermissions("personnel:manage:contractType:list")
	@RequestMapping(value = {"list", ""})
	public String list(ContractType contractType, Model model) {
		model.addAttribute("contractType", contractType);
		return "modules/personnel/manage/contractTypeList";
	}
	
		/**
	 * 合同类型列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:contractType:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ContractType contractType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractType> page = contractTypeService.findPage(new Page<ContractType>(request, response), contractType); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑合同类型表单页面
	 */
	@RequiresPermissions(value={"personnel:manage:contractType:view","personnel:manage:contractType:add","personnel:manage:contractType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ContractType contractType, Model model) {
		model.addAttribute("contractType", contractType);
		return "modules/personnel/manage/contractTypeForm";
	}

	/**
	 * 保存合同类型
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:manage:contractType:add","personnel:manage:contractType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ContractType contractType, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(contractType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		return  contractTypeService.preserve(contractType);//保存

	}
	
	/**
	 * 删除合同类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:contractType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ContractType contractType) {
		AjaxJson j = new AjaxJson();
		contractTypeService.delete(contractType);
		j.setMsg("删除合同类型成功");
		return j;
	}
	
	/**
	 * 批量删除合同类型
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:contractType:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			ContractType contractType = new ContractType();
			contractType.setId(id);
			Contract contract = new Contract();
			contract.setContractType(contractType);
//			staff.setContractType(contractType);
			int count = contractService.count(contract);
			if (count>0){
				ContractType c = contractTypeService.get(id);
				j.setSuccess(false);
				j.setMsg("当前合同类型"+c.getName()+"使用中，无法删除");
				return j;
			}
			contractTypeService.delete(contractTypeService.get(id));
		}
		j.setMsg("删除合同类型成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:contractType:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(ContractType contractType, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "合同类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ContractType> page = contractTypeService.findPage(new Page<ContractType>(request, response, -1), contractType);
    		new ExportExcel("合同类型", ContractType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出合同类型记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:contractType:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ContractType> list = ei.getDataList(ContractType.class);
			for (ContractType contractType : list){
				try{
					contractTypeService.save(contractType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条合同类型记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条合同类型记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入合同类型失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入合同类型数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:manage:contractType:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "合同类型数据导入模板.xlsx";
    		List<ContractType> list = Lists.newArrayList(); 
    		new ExportExcel("合同类型数据", ContractType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}
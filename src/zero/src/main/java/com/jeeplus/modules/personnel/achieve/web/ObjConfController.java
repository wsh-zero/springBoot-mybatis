/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.achieve.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.mapper.StaffMapper;
import com.jeeplus.modules.personnel.plan.entity.Rank;
import com.jeeplus.modules.personnel.plan.service.RankService;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.mapper.OfficeMapper;
import com.jeeplus.modules.sys.utils.UserUtils;
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
import com.jeeplus.modules.personnel.achieve.entity.ObjConf;
import com.jeeplus.modules.personnel.achieve.service.ObjConfService;

/**
 * 对象配置Controller
 * @author ww
 * @version 2019-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/achieve/objConf")
public class ObjConfController extends BaseController {

	@Autowired
	private ObjConfService objConfService;
	@Autowired
	private RankService rankService;
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private OfficeMapper officeMapper;
	
	@ModelAttribute
	public ObjConf get(@RequestParam(required=false) String id) {
		ObjConf entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = objConfService.get(id);
		}
		if (entity == null){
			entity = new ObjConf();
		}
		return entity;
	}
	
	/**
	 * 对象列表页面
	 */
	@RequiresPermissions("personnel:achieve:objConf:list")
	@RequestMapping(value = {"list", ""})
	public String list(ObjConf objConf, Model model) {
		model.addAttribute("objConf", objConf);
		return "modules/personnel/achieve/objConfList";
	}
	
		/**
	 * 对象列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:objConf:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ObjConf objConf, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ObjConf> page = objConfService.findPage(new Page<ObjConf>(request, response), objConf);

		String departname;
		for (ObjConf conf :page.getList()){
		    conf.setRank(rankService.get(conf.getRank().getId()));
		    String[] list =  conf.getDeparts().split(",");
            StringBuffer stringBuffer = new StringBuffer();
		    for (int i = 0;i<list.length;i++){
		        if (i==list.length-1){
                     departname = officeMapper.get(list[i]).getName();
                }else {
                     departname = officeMapper.get(list[i]).getName() + ",";
                }
		        stringBuffer.append(departname);
            }
            conf.setDeparts(stringBuffer.toString());
        }
		return getBootstrapData(page);
	}


	/**
	 * 查看，增加，编辑对象表单页面
	 */
	@RequiresPermissions(value={"personnel:achieve:objConf:view","personnel:achieve:objConf:add","personnel:achieve:objConf:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ObjConf objConf, Model model) {
	    String [] list = objConf.getDeparts().split(",");
	    List<Object> obj = new ArrayList();
	    for(int i =0 ; i<list.length;i++){
	        Map<String, Object> item = new HashMap<>();
	        item.put("value", officeMapper.get(list[i]).getId());
            item.put("label", officeMapper.get(list[i]).getName());
	        obj.add(item);
        }
        model.addAttribute("obj",obj);
		model.addAttribute("objConf", objConf);
		model.addAttribute("rank",rankService.findList(new Rank()));
		return "modules/personnel/achieve/objConfForm";
	}

	/**
	 * 保存对象
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:achieve:objConf:add","personnel:achieve:objConf:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ObjConf objConf, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(objConf);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		try{
			if (objConf.getIsNewRecord()) {
				if (objConf.getGroup() != null) {
					int count = objConfService.count(objConf);
					if (count > 0){
						j.setSuccess(false);
						j.setMsg("考核标准重复");
						return j;
					}
				}
			}else {
				if (objConf.getGroup() != null) {
					ObjConf p = objConfService.find(objConf);
					if (p!=null && !p.getId().equals(objConf.getId())){
						j.setSuccess(false);
						j.setMsg("考核标准重复");
						return j;
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		//新增或编辑表单保存
		objConfService.save(objConf);//保存
		j.setSuccess(true);
		j.setMsg("保存对象成功");
		return j;
	}
	
	/**
	 * 删除对象
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:objConf:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ObjConf objConf) {
		AjaxJson j = new AjaxJson();
		objConfService.delete(objConf);
		j.setMsg("删除对象成功");
		return j;
	}
	
	/**
	 * 批量删除对象
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:objConf:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			objConfService.delete(objConfService.get(id));
		}
		j.setMsg("删除对象成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:objConf:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(ObjConf objConf, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "对象"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ObjConf> page = objConfService.findPage(new Page<ObjConf>(request, response, -1), objConf);
    		new ExportExcel("对象", ObjConf.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出对象记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:objConf:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ObjConf> list = ei.getDataList(ObjConf.class);
			for (ObjConf objConf : list){
				try{
					objConfService.save(objConf);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条对象记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条对象记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入对象失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入对象数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:achieve:objConf:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "对象数据导入模板.xlsx";
    		List<ObjConf> list = Lists.newArrayList(); 
    		new ExportExcel("对象数据", ObjConf.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	/**
	 * 根据发送过来的职级id 查询出存在当前职级的部门
	 */
	@ResponseBody
	@RequestMapping(value = "getDepart")
	public AjaxJson getDepart(@RequestParam String rank, HttpServletRequest request, HttpServletResponse response){
		AjaxJson j =new AjaxJson();
		try{
			List<Object> list = staffMapper.getDepartByRank(rank);
			List<Object> departs = new ArrayList<>();
			for (int i = 0 ; i<list.size();i++){
				HashMap depart = new HashMap();
				Office office = officeMapper.get(list.get(i).toString());
				depart.put("id",list.get(i).toString());
				depart.put("name",office.getName());
				departs.add(depart);
			}
			LinkedHashMap<String,Object> map = new LinkedHashMap<>();
			map.put("list",departs);
			j.setBody(map);
			j.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
		}
		return j;
	}

}
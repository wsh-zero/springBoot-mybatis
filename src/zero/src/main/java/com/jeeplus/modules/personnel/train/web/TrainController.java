/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.train.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.TimeUtils;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import com.jeeplus.modules.personnel.manager.service.StaffService;
import com.jeeplus.modules.personnel.train.entity.TrainRecord;
import com.jeeplus.modules.personnel.train.entity.TrainType;
import com.jeeplus.modules.personnel.train.mapper.TrainRecordMapper;
import com.jeeplus.modules.personnel.train.service.TrainTypeService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
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
import com.jeeplus.modules.personnel.train.entity.Train;
import com.jeeplus.modules.personnel.train.service.TrainService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 培训管理Controller
 * @author 王伟
 * @version 2019-02-19
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/train/train")
public class TrainController extends BaseController {

	@Autowired
	private TrainService trainService;
	@Autowired
	private TrainRecordMapper trainRecordMapper;
	@Autowired
	private SystemService systemService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private TrainTypeService trainTypeService;
	
	@ModelAttribute
	public Train get(@RequestParam(required=false) String id) {
		Train entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = trainService.get(id);
		}
		if (entity == null){
			entity = new Train();
		}
		return entity;
	}
	
		/**
	 * 培训列表数据
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:train:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Train train, boolean isSelf,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Train> page = new Page<>();
		if(isSelf){
			 train.setSelf(true);
			 page = trainService.findMyPage(new Page<Train>(request, response), train);
		}
         else {
			 page = trainService.findPage(new Page<Train>(request, response), train);
		}
		List<Train> list = page.getList();
		for (Train t : list){
			//计算培训时长  精确到小时
			DecimalFormat df = new DecimalFormat("0.0");
			double temp = t.getEndTime().getTime()-t.getStartTime().getTime();
			BigDecimal bigDecimal = new BigDecimal(temp / 1000 / 3600);
			t.setTime(bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()+"h");
		}
		return getBootstrapData(page);
	}
	/**
	 * 参加培训员工列表页面
	 */
//	@RequiresPermissions(value={"personnel:train:train:trainStaff"},logical=Logical.OR)
	@RequestMapping(value = "trainStaff")
		public String trainStaff(TrainRecord trainRecord, HttpServletRequest request, HttpServletResponse response, Model model){
			model.addAttribute("id",trainRecord.getTrain().getId());
			return "modules/personnel/train/trainStaffList";
	}
	/**
	 * 培训员工列表数据
	 */
	@ResponseBody
//	@RequiresPermissions("personnel:train:train:trainStaff")
	@RequestMapping(value = "trainStaffData")
	public Map<String, Object> trainStaffList(String id,HttpServletRequest request, HttpServletResponse response, Model model) {
        Train train = new Train();
		train.setId(id);
		TrainRecord trainRecord = new TrainRecord();
		trainRecord.setTrain(train);
		Page<TrainRecord> page = trainService.getTrainStaff(new Page<TrainRecord>(request, response), trainRecord);
		List<TrainRecord> list = page.getList();
		for (TrainRecord t : list){
			User user = systemService.getUser(t.getUser().getId());
			t.setDepart(user.getOffice());
			//测试阶段使用 User表staff可能为空
			if (user.getStaff()!=null && StringUtils.isNotBlank(user.getStaff().getId())) {
				Staff staff = staffService.get(user.getStaff().getId());
			    t.setStation(staff.getStation());
			}
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，编辑培训员工表单页面
	 */
//	@RequiresPermissions(value={"personnel:train:train:staffview","personnel:train:train:staffedit"},logical=Logical.OR)
	@RequestMapping(value = "staffForm")
	public String staffform( boolean isSelf, TrainRecord trainRecord, Model model) {
		if (StringUtils.isNotBlank(trainRecord.getId())){
			trainRecord = trainRecordMapper.get(trainRecord.getId());
		}
		model.addAttribute("trainRecord", trainRecord);

		return "modules/personnel/train/trainStaffForm";
	}
	/**
	 * 保存培训
	 */
	@ResponseBody
//	@RequiresPermissions(value={"personnel:train:train:staffedit"},logical=Logical.OR)
	@RequestMapping(value = "staffSave")
	public AjaxJson trainStaffSave(TrainRecord trainRecord, Model model,RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
//		String errMsg = beanValidator(trainRecord);
//		if (StringUtils.isNotBlank(errMsg)) {
//			j.setSuccess(false);
//			j.setMsg(errMsg);
//			return j;
//		}
		trainService.staffSave(trainRecord);
		j.setSuccess(true);
		j.setMsg("保存信息成功");
		return j;
	}


	/**
	 * 培训列表页面
	 */
	@RequiresPermissions("personnel:train:train:list")
	@RequestMapping(value = {"list", ""})
	public String list(Train train, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("isSelf", false);
		model.addAttribute("type",trainTypeService.findList(new TrainType()));
		return "modules/personnel/train/trainList";
	}
	/**
	 * 查看，增加，编辑培训表单页面
	 */
	@RequiresPermissions(value={"personnel:train:train:view","personnel:train:train:add","personnel:train:train:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form( boolean isSelf, Train train, Model model) {
		if (StringUtils.isNotBlank(train.getId())){
		if(isSelf){
			trainService.updateReadFlag(train);
			train = trainService.get(train.getId());
		}
		train = trainService.getRecordList(train);
	}
		model.addAttribute("isSelf", isSelf);
		model.addAttribute("train", train);
		model.addAttribute("type",trainTypeService.findList(new TrainType()));

		return "modules/personnel/train/trainForm";
	}

	/**
	 * 保存培训
	 */
	@ResponseBody
	@RequiresPermissions(value={"personnel:train:train:add","personnel:train:train:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Train train, Model model,RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(train);
		if (StringUtils.isNotBlank(errMsg)) {
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(train.getId())) {
			Train e = trainService.get(train.getId());

		}
		int count = train.getEndTime().compareTo(train.getStartTime());
		if (count <0) {
			j.setSuccess(false);
			j.setMsg("结束时间不能小于开始时间");
			return j;
		}
			trainService.save(train);
		if("1".equals(train.getStatus())){
			List<TrainRecord> list = train.getTrainRecordList();
			for (TrainRecord o : list) {
				//发送通知到客户端
							 ServletContext context = SpringContextHolder
									 .getBean(ServletContext.class);
							 new SystemInfoSocketHandler().sendMessageToUser(UserUtils.get(o.getUser().getId()).getLoginName(), "收到一条新通知，请到我的培训查看！");

				}
			}

		j.setMsg("保存通知'" + train.getTitle() + "'成功");
		return j;
	}
	
	/**
	 * 删除培训
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:train:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Train train) {
		AjaxJson j = new AjaxJson();
		trainService.delete(train);
		j.setMsg("删除培训成功");
		return j;
	}
	
	/**
	 * 批量删除培训
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:train:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			trainService.delete(trainService.get(id));
		}
		j.setMsg("删除培训成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:train:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Train train, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "培训"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Train> page = trainService.findPage(new Page<Train>(request, response, -1), train);
    		new ExportExcel("培训", Train.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出培训记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:train:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Train> list = ei.getDataList(Train.class);
			for (Train train : list){
				try{
					trainService.save(train);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条培训记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条培训记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入培训失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入培训数据模板
	 */
	@ResponseBody
	@RequiresPermissions("personnel:train:train:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "培训数据导入模板.xlsx";
    		List<Train> list = Lists.newArrayList(); 
    		new ExportExcel("培训数据", Train.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	/**
	 * 我的通知列表
	 */
	@RequestMapping(value = "self")
	public String selfList(Train train, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("isSelf", true);
		return "modules/personnel/train/trainList";
	}
	/**
	 * 查看我的培训-数据
	 */
	@RequestMapping(value = "viewData")
	@ResponseBody
	public Train viewData(Train train, Model model) {
		if (StringUtils.isNotBlank(train.getId())){
			trainService.updateReadFlag(train);
			return train;
		}
		return null;
	}

	/**
	 * 查看我的培训-发送记录
	 */
	@RequestMapping(value = "viewRecordData")
	@ResponseBody
	public Train viewRecordData(Train train, Model model) {
		if (StringUtils.isNotBlank(train.getId())){
			train = trainService.getRecordList(train);
			return train;
		}
		return null;
	}

	/**
	 * 获取我的培训数目
	 */
	@RequestMapping(value = "self/count")
	@ResponseBody
	public String selfCount(Train train, Model model) {
		train.setSelf(true);
		train.setReadFlag("0");
		return String.valueOf(trainService.findCount(train));
	}
}

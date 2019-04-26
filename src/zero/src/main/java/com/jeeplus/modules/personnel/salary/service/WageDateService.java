/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.CornUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.monitor.entity.ScheduleJob;
import com.jeeplus.modules.monitor.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.personnel.salary.entity.WageDate;
import com.jeeplus.modules.personnel.salary.mapper.WageDateMapper;

/**
 * 工资发放时间设置Service
 * @author 王伟
 * @version 2019-03-20
 */
@Service
@Transactional(readOnly = true)
public class WageDateService extends CrudService<WageDateMapper, WageDate> {
	@Autowired
	private ScheduleJobService scheduleJobService;

	public WageDate get(String id) {
		return super.get(id);
	}
	
	public List<WageDate> findList(WageDate wageDate) {
		return super.findList(wageDate);
	}
	
	public Page<WageDate> findPage(Page<WageDate> page, WageDate wageDate) {
		return super.findPage(page, wageDate);
	}
	
	@Transactional(readOnly = false)
	public void save(WageDate wageDate) {
		super.save(wageDate);
	}
	@Transactional(readOnly = false)
	public AjaxJson presave(WageDate wageDate) {
		AjaxJson j =new AjaxJson();
		if (wageDate.getWageDate()<=wageDate.getSlipeDate()){
            j.setSuccess(false);
            j.setMsg("工资发放时间不能小于或等于工资报表生成时间");
            return j;
		}
		ScheduleJob scheduleJob = new ScheduleJob();
        if (wageDate.getIsNewRecord()){
			Calendar calendar = Calendar.getInstance();
			calendar.set(0,0,wageDate.getSlipeDate(),0,0,0);
            scheduleJob.setCronExpression(CornUtils.getCron(calendar.getTime()) );
            scheduleJob.setGroup("1");
			scheduleJob.setName("工资报表生成");
            scheduleJob.setClassName("com.jeeplus.modules.monitor.task.SalaryTask");
            scheduleJob.setIsInfo("1");
            scheduleJob.setStatus("0");
            scheduleJobService.save(scheduleJob);
            wageDate.setScheduleJob(scheduleJob.getId());

			super.save(wageDate);
			j.setMsg("保存成功，请到定时任务页面启动工资生成功能");
		}
		else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(0,0,wageDate.getSlipeDate(),0,0,0);
			scheduleJob.setCronExpression(CornUtils.getCron(calendar.getTime()) );
			scheduleJob.setGroup("1");
			scheduleJob.setName("工资条生成");
			scheduleJob.setClassName("com.jeeplus.modules.monitor.task.SalaryTask");
			scheduleJob.setIsInfo("1");
			scheduleJob.setStatus("0");
			scheduleJob.setId(wageDate.getScheduleJob());
			scheduleJob.setIsNewRecord(false);
			scheduleJobService.save(scheduleJob);

			super.save(wageDate);
			j.setMsg("工资生成功能已改变，请重新启动");
		}

		return j;
	}
	
	@Transactional(readOnly = false)
	public void delete(WageDate wageDate) {
	    ScheduleJob scheduleJob = scheduleJobService.get(wageDate.getScheduleJob());
		scheduleJobService.delete(scheduleJob);
		super.delete(wageDate);
	}
	
}
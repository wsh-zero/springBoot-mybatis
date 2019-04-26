/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 岗位类别Entity
 * @author 王伟
 * @version 2019-02-14
 */
public class JobCategory extends DataEntity<JobCategory> {
	
	private static final long serialVersionUID = 1L;
	private String jobType;		// 岗位类别
	
	public JobCategory() {
		super();
	}

	public JobCategory(String id){
		super(id);
	}

	@ExcelField(title="岗位类型", align=2, sort=1)
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
}
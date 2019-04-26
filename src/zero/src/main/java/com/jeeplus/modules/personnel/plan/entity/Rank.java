/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 职级管理Entity
 * @author 王伟
 * @version 2019-02-15
 */
public class Rank extends DataEntity<Rank> {
	
	private static final long serialVersionUID = 1L;
	private String rankName;		// 职级名称
	
	public Rank() {
		super();
	}

	public Rank(String id){
		super(id);
	}

	@ExcelField(title="职级名称", align=2, sort=1)
	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	
}
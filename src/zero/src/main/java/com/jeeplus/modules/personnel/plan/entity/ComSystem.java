/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.plan.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 制度管理Entity
 * @author 王伟
 * @version 2019-02-14
 */
public class ComSystem extends DataEntity<ComSystem> {
	
	private static final long serialVersionUID = 1L;
	private String sysCode;		// 制度编号
	private String sysName;		// 制度名称
	private String sysContent;		// 制度内容
	
	public ComSystem() {
		super();
	}

	public ComSystem(String id){
		super(id);
	}

	@ExcelField(title="制度编号", align=2, sort=1)
	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	@ExcelField(title="制度名称", align=2, sort=2)
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	@ExcelField(title="制度内容", align=2, sort=3)
	public String getSysContent() {
		return sysContent;
	}

	public void setSysContent(String sysContent) {
		this.sysContent = sysContent;
	}
	
}
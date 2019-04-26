/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oatest.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 随意测试Entity
 * @author xy
 * @version 2019-01-25
 */
public class Oatest extends DataEntity<Oatest> {
	
	private static final long serialVersionUID = 1L;
	private String username;		// 用户名
	private String userpwd;		// 用户密码
	
	public Oatest() {
		super();
	}

	public Oatest(String id){
		super(id);
	}

	@ExcelField(title="用户名", align=2, sort=7)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelField(title="用户密码", align=2, sort=8)
	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	
}
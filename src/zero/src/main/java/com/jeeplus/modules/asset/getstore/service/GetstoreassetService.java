/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.asset.getstore.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.asset.getstore.entity.Getstoreasset;
import com.jeeplus.modules.asset.getstore.mapper.GetstoreassetMapper;

/**
 * 固定资产出库管理Service
 * @author xy
 * @version 2019-02-22
 */
@Service
@Transactional(readOnly = true)
public class GetstoreassetService extends CrudService<GetstoreassetMapper, Getstoreasset> {

	@Autowired
	private GetstoreassetMapper getstoreassetMapper;
	@Autowired
	UserMapper userMapper;

	public Getstoreasset get(String id) {
		return super.get(id);
	}
	
	public List<Getstoreasset> findList(Getstoreasset getstoreasset) {
		return super.findList(getstoreasset);
	}
	
	public Page<Getstoreasset> findPage(Page<Getstoreasset> page, Getstoreasset getstoreasset) {
		return super.findPage(page, getstoreasset);
	}
	
	@Transactional(readOnly = false)
	public void save(Getstoreasset getstoreasset) {
		super.save(getstoreasset);
	}
	
	@Transactional(readOnly = false)
	public void delete(Getstoreasset getstoreasset) {
		super.delete(getstoreasset);
	}

	public List<User> getStoreList(String userids){
		System.out.println("userids="+userids);
		Getstoreasset getstoreasset=new Getstoreasset();
		List<User> userpersonlist=new ArrayList<>();
		if(!StringUtils.isEmpty(userids)) {
			String[] ids=userids.split(",");
			for(String id:ids){
				User user=userMapper.getUsers(id);
				userpersonlist.add(user);
			}

		}
		return userpersonlist;
	}
	
}
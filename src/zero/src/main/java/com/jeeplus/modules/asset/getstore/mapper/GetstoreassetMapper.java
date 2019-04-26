/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.asset.getstore.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.asset.getstore.entity.Getstoreasset;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 固定资产出库管理MAPPER接口
 * @author xy
 * @version 2019-02-22
 */
@MyBatisMapper
public interface GetstoreassetMapper extends BaseMapper<Getstoreasset> {

//    List<User> getUser(@Param("userids") List userids);
//    List<User> getUser(List<String> list);

}
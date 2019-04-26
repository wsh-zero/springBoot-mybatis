/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.manager.entity.Contact;
import com.jeeplus.modules.personnel.manager.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工基本信息MAPPER接口
 * @author 王伟
 * @version 2019-01-30
 */
@MyBatisMapper
public interface StaffMapper extends BaseMapper<Staff> {

    Staff find(Staff staff);

    int count(Staff staff) ;

    Integer getMaxcode();

    Staff findLeader(@Param("code") String code);

    List<Staff> getStaff();

    List<Staff> getStaffByStation(@Param("station") String station);

    List<Object> getDepartByRank(@Param("rank") String rank);


}
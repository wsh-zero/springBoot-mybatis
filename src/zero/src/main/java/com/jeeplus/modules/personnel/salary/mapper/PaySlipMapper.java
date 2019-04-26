/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.salary.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.salary.entity.PaySlip;

/**
 * 工资条MAPPER接口
 * @author ww
 * @version 2019-03-29
 */
@MyBatisMapper
public interface PaySlipMapper extends BaseMapper<PaySlip> {

    void add(PaySlip paySlip);
	
}
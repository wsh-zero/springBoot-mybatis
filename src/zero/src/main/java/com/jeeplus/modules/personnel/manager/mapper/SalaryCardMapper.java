/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.manager.entity.SalaryCard;

import java.util.List;

/**
 * 工资卡MAPPER接口
 * @author 王伟
 * @version 2019-01-31
 */
@MyBatisMapper
public interface SalaryCardMapper extends BaseMapper<SalaryCard> {

    SalaryCard find(SalaryCard salaryCard);

    SalaryCard getName(String id);

    int count(SalaryCard salaryCard) ;

    /**
     * 获取状态为启用的工资卡
     */
    List<SalaryCard> getActive();

}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.personnel.manager.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.personnel.manager.entity.Contract;

/**
 * 合同管理MAPPER接口
 * @author 王伟
 * @version 2019-02-11
 */
@MyBatisMapper
public interface ContractMapper extends BaseMapper<Contract> {
    Contract find(Contract salaryCard);

    Contract getName(String id);

    int count(Contract salaryCard) ;


}
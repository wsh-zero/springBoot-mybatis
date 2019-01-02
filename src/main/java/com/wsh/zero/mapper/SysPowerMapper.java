package com.wsh.zero.mapper;

import com.wsh.zero.entity.SysPowerEntity;
import com.wsh.zero.mapper.base.BaseMapper;
import com.wsh.zero.query.SysPowerQuery;
import com.wsh.zero.vo.SysPowerVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SysPowerMapper extends BaseMapper<SysPowerEntity, SysPowerQuery, SysPowerVO> {
    String[] getAllPowerPath();

    List<SysPowerVO> getPowers();

    int delByParent(@Param("parent") String parent);

    int delByNotPrimaryKey(@Param("id") String id);

    Set<String> getPowerPathByUserAmount(@Param("userAmount") String userAmount);
}
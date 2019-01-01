package com.wsh.zero.entity;

import com.wsh.zero.entity.base.BaseEntity;
import lombok.Data;

import java.util.Set;

@Data
public class SysRoleEntity extends BaseEntity {
    private String id;
    private String roleName;
    private String[] powerIds;
    private Set<SysPowerEntity> powerList;
}
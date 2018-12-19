package com.wsh.zero.entity;

import lombok.Data;

import java.util.Set;

@Data
public class SysRoleEntity {
    private String id;
    private String roleName;
    private String[] powerIds;
    private Set<SysPowerEntity> powerList;
}
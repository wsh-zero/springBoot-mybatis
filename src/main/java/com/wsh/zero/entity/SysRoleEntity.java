package com.wsh.zero.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysRoleEntity {
    private String id;
    private String roleName;
    private List<SysPowerEntity> powerList;
}
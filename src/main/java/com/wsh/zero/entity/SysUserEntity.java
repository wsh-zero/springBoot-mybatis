package com.wsh.zero.entity;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SysUserEntity {
    private String id;
    private String userName;
    private String userAmount;
    private String userPwd;
    private Integer frozen;
    private String[] roleIds;
    private Set<SysRoleEntity> roleList;
}
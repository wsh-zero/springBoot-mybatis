package com.wsh.zero.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysUserEntity {
    private String id;
    private String userName;
    private String userAmount;
    private String userPwd;
    private Integer frozen;
    private List<SysRoleEntity> roleList;
}
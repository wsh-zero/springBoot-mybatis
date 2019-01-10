package com.wsh.zero.entity;

import com.wsh.zero.entity.base.BaseEntity;
import lombok.Data;

import java.util.Set;

@Data
public class SysUserEntity extends BaseEntity {
    private String id;
    private String userName;
    private String userAmount;
    private String userPwd;
    private Integer frozen;
    private String picture;
    private String[] roleIds;
    private Set<SysRoleEntity> roleList;
}
package com.wsh.zero.vo;

import lombok.Data;

import java.util.List;

/**
 * 传输到页面的实体类
 */
@Data
public class SysUserVO {
    private String id;
    private String userName;
    private String userAmount;
    private String userPwd;
    private List<SysRoleVO> roleList;
}
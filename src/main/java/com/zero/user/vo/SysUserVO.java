package com.zero.user.vo;

import lombok.Data;

/**
 * 传输到页面的实体类
 */
@Data
public class SysUserVO {
    private String id;
    private String userName;
    private String userAmount;
    private String userPwd;
}
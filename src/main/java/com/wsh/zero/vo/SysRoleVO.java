package com.wsh.zero.vo;

import lombok.Data;

import java.util.List;

/**
 * 传输到页面的实体类
 */
@Data
public class SysRoleVO {
    private String id;
    private String roleName;
    private List<SysPowerVO> powerList;
}
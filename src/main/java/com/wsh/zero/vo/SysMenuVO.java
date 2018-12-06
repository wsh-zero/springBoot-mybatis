package com.wsh.zero.vo;

import lombok.Data;

import java.util.List;


@Data
public class SysMenuVO {
    private String id;
    private String title;
    private Boolean spread;
    private String icon;
    private String jump;
    private String parent;
    private List list;
}
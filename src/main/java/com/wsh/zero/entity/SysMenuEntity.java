package com.wsh.zero.entity;

import lombok.Data;

@Data
public class SysMenuEntity {
    private String id;
    private String name;
    private String title;
    private String icon;
    private String jump;
    private Boolean spread;
    private String parent;
    private Integer level;
}
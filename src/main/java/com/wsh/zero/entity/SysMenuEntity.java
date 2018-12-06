package com.wsh.zero.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SysMenuEntity {
    private String id;
    private String title;
    private String icon;
    private String jump;
    private Boolean spread;
    private String parent;
    private BigDecimal level;
}
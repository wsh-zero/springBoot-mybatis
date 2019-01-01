package com.wsh.zero.vo;

import lombok.Data;


@Data
public class MenuTreeVO {
    private String id;
    private String name;
    private String parent;
    private String jump;
    private String menuId;
}
package com.wsh.zero.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenuVO {
    private String id;
    private String title;
    private Boolean spread;
    private String icon;
    private String jump;
    private String parent;
    private List list;
}
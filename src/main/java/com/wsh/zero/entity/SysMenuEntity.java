package com.wsh.zero.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

@Data
public class SysMenuEntity {
    private String id;
    private String name;
    private String title;
    private String icon;
    private String jump;
    private Boolean spread;
    @NotBlank(message = "父级编号不能为NULL")
    private String parent;
    private BigDecimal level;
}
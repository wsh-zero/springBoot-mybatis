package com.wsh.zero.entity;

import lombok.Data;

@Data
public class SysPowerEntity {
    private String id;
    private String powerName;
    private String powerPath;
    private String parent;
    private Integer powerType = 1;
    private Integer powerState = 0;
}
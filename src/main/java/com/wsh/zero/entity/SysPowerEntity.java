package com.wsh.zero.entity;

import com.wsh.zero.entity.base.BaseEntity;
import lombok.Data;

@Data
public class SysPowerEntity extends BaseEntity {
    private String id;
    private String powerName;
    private String powerPath;
    private String parent;
    private Integer powerType = 1;
}
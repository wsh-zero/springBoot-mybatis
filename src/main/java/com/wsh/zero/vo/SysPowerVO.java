package com.wsh.zero.vo;

import lombok.Data;

/**
 * 传输到页面的实体类
 */
@Data
public class SysPowerVO {
    private String id;
    private String powerName;
    private String name;
    private String powerPath;
    private String parent;
    private Integer powerType = 1;
    private Integer powerState = 0;
    private String menuId;
    private String menuJump;

    public String getName() {
        return this.powerName;
    }
}
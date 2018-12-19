package com.wsh.zero.vo;

import lombok.Data;

/**
 * 传输到页面的实体类
 */
@Data
public class SysPowerVO {
    private String id;
    private String powerName;
    private String powerPath;
    private String parent;
}
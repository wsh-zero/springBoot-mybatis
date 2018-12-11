package com.wsh.zero.entity;

import com.wsh.util.ConsotEnum;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SysLogEntity {
    private String id;
    private String title;
    private String operation;
    private String userName;
    private ConsotEnum level;
    private String data;
    /**
     * 操作IP地址
     */
    private String remoteAddr;
    /**
     * 操作方法
     */
    private String classMethod;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 浏览器信息
     */
    private String browser;
    /**
     * 方法执行时长
     */
    private Long useTime;
    /**
     * 创建时间
     */
    private Timestamp gmtTime;
}
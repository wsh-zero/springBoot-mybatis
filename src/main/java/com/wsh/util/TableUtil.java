package com.wsh.util;

import lombok.Data;

import java.util.List;

@Data
public class TableUtil<T> {
    private int code;
    private String msg = "获取列表数据成功";
    private int count;
    private List<T> data;
}

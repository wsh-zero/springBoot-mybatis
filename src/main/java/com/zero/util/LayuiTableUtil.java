package com.zero.util;

import java.util.List;

public class LayuiTableUtil<T> {
    private int code;
    private String msg = "请求成功";
    private int count;
    private List<T> data;
    private static LayuiTableUtil INSTANCE;

    static {
        INSTANCE = new LayuiTableUtil();
    }

    private LayuiTableUtil() {
    }

    public static LayuiTableUtil getInstance() {
        return INSTANCE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

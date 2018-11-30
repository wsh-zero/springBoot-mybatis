package com.zero.util;

import lombok.Data;

@Data
public class ResultUtil<T> {
    private int code;
    private String msg;
    private T data;

    public ResultUtil(int code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    public ResultUtil(int code, String msg) {
        this(msg);
        this.code = code;
    }

    public ResultUtil(String msg) {
        this.msg = msg;
    }

    public static ResultUtil failed(int code, String msg) {
        return new ResultUtil<>(code, msg, null);
    }
}

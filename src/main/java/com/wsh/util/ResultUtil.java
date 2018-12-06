package com.wsh.util;

import lombok.Data;

@Data
public class ResultUtil {
    private int code;
    private String msg;
    private Object data;

    private ResultUtil(int code, String msg, Object data) {
        this(code, msg);
        this.data = data;
    }

    private ResultUtil(int code, String msg) {
        this(msg);
        this.code = code;
    }

    private ResultUtil(String msg, Object data) {
        this(msg);
        this.data = data;
    }

    private ResultUtil(String msg) {
        this.msg = msg;
    }

    public static ResultUtil success(String msg, Object data) {
        return new ResultUtil(msg, data);
    }

    public static ResultUtil success(String msg) {
        return new ResultUtil(msg);
    }

    public static ResultUtil failed(int code, String msg) {
        return new ResultUtil(code, msg);
    }

    public static ResultUtil failed(int code, String msg, Object data) {
        return new ResultUtil(code, msg, data);
    }
}

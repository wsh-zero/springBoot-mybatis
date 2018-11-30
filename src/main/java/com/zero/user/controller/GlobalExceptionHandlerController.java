package com.zero.user.controller;

import com.zero.util.ResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 18011618
 * @Description 全局异常拦截器
 * @Date 16:38 2018/7/5
 * @Modify By
 */
@RestControllerAdvice
public class GlobalExceptionHandlerController {
    @ExceptionHandler({Exception.class})
    public ResultUtil exception(Exception ex) {
        return resultFormat(ex);
    }

    private <T extends Throwable> ResultUtil resultFormat(T ex) {
        ex.printStackTrace();
        return ResultUtil.failed(404, ex.getMessage());
    }

}
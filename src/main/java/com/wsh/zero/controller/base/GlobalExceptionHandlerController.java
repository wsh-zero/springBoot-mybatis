package com.wsh.zero.controller.base;

import com.wsh.util.ResultUtil;
import org.apache.shiro.ShiroException;
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

    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public ResultUtil handle401() {
        return ResultUtil.failed(401, "您没有权限访问！");
    }

    @ExceptionHandler({Exception.class})
    public ResultUtil exception(Exception ex) {
        return resultFormat(ex);
    }

    private <T extends Throwable> ResultUtil resultFormat(T ex) {
        ex.printStackTrace();
        return ResultUtil.failed(500, ex.getMessage());
    }

}
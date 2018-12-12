package com.wsh.zero.controller.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.wsh.util.Consot;
import com.wsh.util.ConsotEnum;
import com.wsh.util.Utils;
import com.wsh.zero.controller.aop.anno.SysLogTag;
import com.wsh.zero.entity.SysLogEntity;
import com.wsh.zero.mapper.SysLogMapper;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 系统日志切面
 *
 * @author zhuzhe
 * @date 2018/6/4 9:27
 * @email 1529949535@qq.com
 */
@Aspect  // 使用@Aspect注解声明一个切面
@Component
public class SysLogAspect {
    @Autowired
    private SysLogMapper sysLogMapper;
    private SysLogEntity entity;
    private Map<Object, Object> dataMap = new LinkedHashMap<>();
    private Long startTime;

    //表示匹配com.savage.server包及其子包下的所有方法
//    @Pointcut("execution(* com.wsh.zero.controller..*.*(..))")
    @Pointcut("@annotation(com.wsh.zero.controller.aop.anno.SysLogTag)")
    public void Pointcut() {
    }


//    //前置通知
//    @Before("Pointcut()")
//    public void beforeMethod(JoinPoint joinPoint) {
//        System.out.println("调用了前置通知");
//
//    }
//
//    //@After: 后置通知
//    @After("Pointcut()")
//    public void afterMethod(JoinPoint joinPoint) {
//        System.out.println("调用了后置通知");
//    }
//
//    //@AfterRunning: 返回通知 rsult为返回内容
//    @AfterReturning(value = "Pointcut()", returning = "result")
//    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
//        System.out.println("调用了返回通知");
//    }

    //@AfterThrowing: 异常通知
    @AfterThrowing(value = "Pointcut()", throwing = "e")
    public void afterReturningMethod(JoinPoint joinPoint, Exception e) {
        //当controller没有捕获到异常时会进入
        System.out.println("=====进入异常日志类======");
        if (null == entity || Strings.isNullOrEmpty(entity.getId())) {
            setEntity(joinPoint);
        }
        dataMap.put("response", e);
        entity.setData(JSON.toJSONString(dataMap));
        entity.setLevel(ConsotEnum.ERROR);
        entity.setUseTime(System.currentTimeMillis() - startTime);
        sysLogMapper.save(entity);
    }

    //@Around：环绕通知
    @Around("Pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        setEntity(pjp);
        Object object = pjp.proceed();
        try {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(object));
            switch (String.valueOf(jsonObject.get("code"))) {
                case "0":
                    entity.setLevel(ConsotEnum.INFO);
                    break;
                case "1":
                    entity.setLevel(ConsotEnum.WARNING);
                    break;
                default:
                    entity.setLevel(ConsotEnum.ERROR);
                    break;
            }
        } catch (Exception e) {
            entity.setLevel(ConsotEnum.ERROR);
        }
        entity.setUseTime(System.currentTimeMillis() - startTime);

        dataMap.put("response", object);
        entity.setData(JSON.toJSONString(dataMap));
        sysLogMapper.save(entity);
        return object;
    }

    public void setEntity(JoinPoint pjp) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        entity = new SysLogEntity();
        entity.setId(Utils.UUID());
        String classMethod = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
        //获取传入目标方法的参数
        Object[] args = pjp.getArgs();
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            params.append(args[i]);
            if (i < args.length - 1) {
                params.append(",");
            }
        }
        Object userName;
        if (Objects.equals(classMethod, Consot.LOGIN_PACKAGE_NAME)) {
            userName = args[0];
        } else {
            userName = SecurityUtils.getSubject().getPrincipal();
        }
        entity.setUserName(null == userName ? "游客" : (String) userName);
        entity.setClassMethod(classMethod);

        entity.setRequestUri(request.getRequestURL().toString());
        entity.setRemoteAddr(Utils.getClientIp(request));
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Map<String, String> browserMap = Utils.getOsAndBrowserInfo(request);
        entity.setBrowser(browserMap.get("os") + "-" + browserMap.get("browser"));
        SysLogTag log = method.getAnnotation(SysLogTag.class);
        if (null != log) {
            //注解上的描述
            entity.setTitle(log.value());
            entity.setOperation(log.operation());
        }
        startTime = System.currentTimeMillis();
        entity.setGmtTime(new Timestamp(startTime));
        dataMap.put("params", params);
    }
}

package com.wsh.zero.controller.aop.aspect;

import com.alibaba.fastjson.JSON;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

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
        dataMap.put("response", e);
        entity.setData(JSON.toJSONString(dataMap));
        entity.setLevel(ConsotEnum.ERROR);
        entity.setUseTime(System.currentTimeMillis() - startTime);
        sysLogMapper.save(entity);
    }

    //@Around：环绕通知
    @Around("Pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object userName = SecurityUtils.getSubject().getPrincipal();
        entity = new SysLogEntity();
        entity.setId(Utils.UUID());
        entity.setUserName(null == userName ? "游客" : (String) userName);
        entity.setClassMethod(pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        //获取传入目标方法的参数
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            if (o instanceof ServletRequest || (o instanceof ServletResponse) || o instanceof MultipartFile) {
                args[i] = o.toString();
            }
        }
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
        dataMap.put("params", args.length > 0 ? JSON.toJSONString(args) : null);
        Object object = pjp.proceed();
        entity.setUseTime(System.currentTimeMillis() - startTime);
        entity.setLevel(ConsotEnum.INFO);
        dataMap.put("response", object);
        entity.setData(JSON.toJSONString(dataMap));
        sysLogMapper.save(entity);
        return object;
    }
}

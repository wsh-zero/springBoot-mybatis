package com.wsh.config.shiro;

import com.google.common.base.Strings;
import com.wsh.zero.mapper.SysPowerMapper;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //https://blog.csdn.net/qq_35659877/article/details/83379009
    //https://blog.csdn.net/Joe_Wang1/article/details/81635353
    //https://blog.csdn.net/pingdouble/article/details/79165545
    //将自己的验证方式加入容器
    @Autowired
    private SysPowerMapper sysPowerMapper;

    @Bean
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/notLogin");//登录连接
//        shiroFilterFactoryBean.setSuccessUrl("/index");//登录成功后跳转的连接
//        shiroFilterFactoryBean.setUnauthorizedUrl("/401"); //未授权跳转页面
        //拦截器
        Map<String, String> map = new LinkedHashMap<>();
        //登出
        map.put("/logout", "logout");
        /**
         * 过滤链定义，从上向下顺序执行，/**放在最下面，过滤链的最后一关，表示除去以上各环节，剩余url的都需要验证
         * authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
         */
        map.put("/*.ico", "anon");
        map.put("/json/**", "anon");
        map.put("/layui/**", "anon");
        map.put("/src/*.js*", "anon");
        map.put("/src/controller/**", "anon");
        map.put("/src/lib/**", "anon");
        map.put("/src/style/**", "anon");
        map.put("/src/views/user/*.html", "anon");
        //登录、重置密码、注册接口可以匿名访问
        map.put("/login", "anon");
        map.put("/register", "anon");
        map.put("/reset/pwd", "anon");
        //自定义加载权限资源关系
        String[] powerPath = sysPowerMapper.getPowerPathAll();
        if (null != powerPath && powerPath.length > 0) {
            for (String resources : powerPath) {
                if (!Strings.isNullOrEmpty(resources)) {
                    String permission = "perms[" + resources + "]";
                    map.put(resources, permission);
                }
            }
        }
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

//    /**
//     * 限制同一账号登录同时登录人数控制
//     *
//     * @return
//     */
//    public KickoutSessionControlFilter kickoutSessionControlFilter() {
//        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
//        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
//        //这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
//        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
//        kickoutSessionControlFilter.setCacheManager(cacheManager());
//        //用于根据会话ID，获取会话进行踢出操作的；
//        kickoutSessionControlFilter.setSessionManager(sessionManager());
//        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
//        kickoutSessionControlFilter.setKickoutAfter(false);
//        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
//        kickoutSessionControlFilter.setMaxSession(1);
//        //被踢出后重定向到的地址；
//        kickoutSessionControlFilter.setKickoutUrl("kickout");
//        return kickoutSessionControlFilter;
//    }


}

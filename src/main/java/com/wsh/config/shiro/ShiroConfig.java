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
    //https://www.jianshu.com/p/9bfa22b0e905
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
        /**
         * 过滤链定义，从上向下顺序执行，/**放在最下面，过滤链的最后一关，表示除去以上各环节，剩余url的都需要验证
         * authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
         */
        map.put("/logout", "logout");
        map.put("/static/**", "anon");
        /**
         * swagger-ui 接口放行
         */
//        map.put("/swagger-ui.html/**", "anon");
//        map.put("/swagger-resources/**", "anon");
//        map.put("/v2/api-docs/**", "anon");
//        map.put("/webjars/springfox-swagger-ui/**", "anon");
//        map.put("/data:image**", "anon");
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

}

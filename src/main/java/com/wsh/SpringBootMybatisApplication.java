package com.wsh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// Swagger2访问 http://localhost/swagger-ui.html
@SpringBootApplication
@MapperScan("com.wsh.zero.mapper")
//扫描位置要清楚，shiro swagger2没神效都是因为扫描位置配置范围错误引起
@ComponentScan({"com.wsh.zero.controller", "com.wsh.zero.service", "com.wsh.config", "com.wsh.util"})
@EnableTransactionManagement//事务管理
@EnableSwagger2
public class SpringBootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
    }
}

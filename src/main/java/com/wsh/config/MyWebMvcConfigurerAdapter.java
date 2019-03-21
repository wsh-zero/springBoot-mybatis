package com.wsh.config;


import com.wsh.util.FileUtil;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(FileUtil.RELATIVE_PATH + "**").addResourceLocations("file:" + FileUtil.ROOT_PATH);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大MB
        factory.setMaxFileSize(DataSize.ofMegabytes(100L));
        //设置总上传文件总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(200L));
        return factory.createMultipartConfig();
    }
}
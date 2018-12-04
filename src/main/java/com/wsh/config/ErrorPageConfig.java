package com.wsh.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author zero
 * @ClassName: ErrorPageConfig
 * @Description: 配置错误返回信息
 * @date 2018年4月3日 下午3:45:49
 */
@Configuration
public class ErrorPageConfig {
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400");
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");

                container.addErrorPages(error400Page, error401Page, error404Page, error500Page);
            }
        };
    }
}

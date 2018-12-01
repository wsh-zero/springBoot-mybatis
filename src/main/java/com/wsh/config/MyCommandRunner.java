package com.wsh.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * springBoot 启动完后会调用CommandLineRunner run方法的所有实现
 * 这里实现启动后打开谷歌浏览器，需要配置谷歌浏览器路径
 */
@Component
public class MyCommandRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(MyCommandRunner.class);
    @Value("${spring.web.loginurl}")
    private String loginUrl;

    @Value("${spring.web.googleexcute}")
    private String googleExcutePath;

    @Value("${spring.auto.openurl}")
    private boolean isOpen;

    @Override
    public void run(String... args) {
        if (isOpen) {
            String cmd = googleExcutePath + " " + loginUrl;
            Runtime run = Runtime.getRuntime();
            try {
                run.exec(cmd);
                logger.debug("启动浏览器打开项目成功");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }
}

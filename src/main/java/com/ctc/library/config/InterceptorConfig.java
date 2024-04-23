package com.ctc.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:/E:/学习文件夹/Java Web技术/课程设计/课设/library/vue/src/assets/user/");
        registry.addResourceHandler("/book/**").addResourceLocations("file:/E:/学习文件夹/Java Web技术/课程设计/课设/library/vue/src/assets/book/");
    }
}


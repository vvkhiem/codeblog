package com.vuvankhiem.blogzine;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.MultipartConfigElement;
import java.nio.charset.StandardCharsets;
import java.util.List;


@SpringBootApplication(scanBasePackages = {"com.vuvankhiem.blogzine"})
public class BlogzineApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(BlogzineApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/avatar/**").addResourceLocations("classpath:/static/us/assets/images/avatar/");
        registry.addResourceHandler("/public/image/**").addResourceLocations("classpath:/static/uploadmedia/images/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/ckfinder/**").addResourceLocations("classpath:/static/ckfinder/");
        super.addResourceHandlers(registry);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement () {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize(DataSize.parse("10MB"));
        multipartConfigFactory.setMaxRequestSize(DataSize.parse("10MB"));
        return multipartConfigFactory.createMultipartConfig();
    }
}

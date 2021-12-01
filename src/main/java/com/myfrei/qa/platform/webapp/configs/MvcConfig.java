package com.myfrei.qa.platform.webapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"
    };


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/mainPage").setViewName("mainPage");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/question/{id}").setViewName("question");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/login").setViewName("loginPage");
        registry.addViewController("/question/add").setViewName("add_question");
        registry.addViewController("/header").setViewName("tools/header");
        registry.addViewController("/sidebar").setViewName("tools/sidebar");
        registry.addViewController("/footer").setViewName("tools/footer");
        registry.addViewController("/hsf").setViewName("header_sidebar_footer");

        registry.addViewController("/questions").setViewName("questionsTab");
        registry.addViewController("/questions/tagged/{id}").setViewName("questionsByTag");
        registry.addViewController("/unanswered").setViewName("unansweredTab");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
}
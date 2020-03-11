package com.dhitha.codeforum.common.config.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("home.html");
    registry.addViewController("/home").setViewName("home.html");
    registry.addViewController("/login").setViewName("login.html");
  }
}

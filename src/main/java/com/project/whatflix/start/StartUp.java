package com.project.whatflix.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


@SpringBootApplication
public class StartUp {
  public static void main(String[] args) {
    SpringApplication.run(StartUp.class,args);
  }
}
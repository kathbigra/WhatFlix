package com.project.whatflix.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
    "com.project.whatflix.config",
    "com.project.whatflix.start",
    "com.project.whatflix.services",
    "com.project.whatflix.model",
    "com.project.whatflix.listeners",
    "com.project.whatflix.jobs",})
public class StartUp {
  public static void main(String[] args) {
    SpringApplication.run(StartUp.class, args);
  }
}
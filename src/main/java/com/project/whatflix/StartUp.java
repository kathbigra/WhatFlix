package com.project.whatflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
    "com.project.whatflix"})
public class StartUp {
  public static void main(String[] args) {
    SpringApplication.run(StartUp.class, args);
  }
}
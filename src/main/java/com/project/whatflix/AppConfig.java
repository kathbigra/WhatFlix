package com.project.whatflix;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.project.whatflix"})
@EntityScan(basePackages = {"com.project.whatflix.model"})
public class AppConfig {
}

package com.crm.verification.core;

import com.crm.verification.core.common.UrlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(UrlProperties.class)
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}

package com.crm.verification.core;

import com.crm.verification.core.common.UrlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(UrlProperties.class)
@EnableJpaAuditing
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}

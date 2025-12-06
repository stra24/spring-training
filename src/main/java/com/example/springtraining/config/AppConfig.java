package com.example.springtraining.config;

import java.time.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

  @Bean(name = "clock")
  public Clock systemClock() {
    log.info("Clock の Bean を作成して箱に入れます");
    return Clock.systemDefaultZone();
  }
}
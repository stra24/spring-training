package com.example.springtraining.config;

import jakarta.annotation.PostConstruct;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

  @Bean(name = "clock")
  public Clock systemClock() {
    log.info("Clock の Bean を作成して箱に入れます");
    return Clock.systemDefaultZone();
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @PostConstruct
  public void init() throws Exception {
    // Windows環境でコンソールが文字化けしないようにする設定。
    System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
    System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
  }
}
package com.example.springtraining.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

  private static final Logger log = LoggerFactory.getLogger(HelloService.class);

  public HelloService() {
    log.info("HelloService の Bean を作成して箱に入れます");
  }

  public String greet(String name) {
    return "Hello, " + name + "!";
  }
}
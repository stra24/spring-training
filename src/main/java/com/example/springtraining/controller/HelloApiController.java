package com.example.springtraining.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloApiController {

  @GetMapping("/hello-text")
  public String helloText() {
    return "hello"; // プレーンテキストとして返る
  }

  @GetMapping("/hello-json")
  public Map<String, String> helloJson() {
    return Map.of("message", "hello"); // → { "message": "hello" } というJSONにシリアライズされる
  }
}

package com.example.springtraining.controller.api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloApiController {

  @GetMapping("/text")
  public String getText() {
    // "hello" というテキストがレスポンスボディの内容として返される。
    return "hello";
  }

  @GetMapping("/json")
  public Map<String, String> getJson() {
    // { "message": "hello" } というJSONがレスポンスボディの内容として返される。
    return Map.of("message", "hello");
  }
}
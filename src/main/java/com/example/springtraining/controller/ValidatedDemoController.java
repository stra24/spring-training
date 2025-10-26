package com.example.springtraining.controller;

import com.example.springtraining.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/validated")
public class ValidatedDemoController {

  private final AccountService service;

  @GetMapping
  public String page() {
    return "validated";
  }

  @PostMapping("/register")
  public String register(
      @RequestParam String name,
      @RequestParam String email,
      @RequestParam int age,
      Model model
  ) {
    var msg = service.register(name, email, age); // ← メソッドの呼び出し時に引数検証
    model.addAttribute("result", msg);
    model.addAttribute("active", "register");
    return "validated";
  }

  @PostMapping("/load")
  public String load(
      @RequestParam String id,
      Model model
  ) {
    var dto = service.load(id); // ← メソッドの呼び出し時に戻り値検証
    model.addAttribute("result", dto.id() + ":" + dto.name());
    model.addAttribute("active", "load");
    return "validated";
  }
}
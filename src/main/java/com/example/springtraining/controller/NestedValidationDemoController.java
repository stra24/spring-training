package com.example.springtraining.controller;

import com.example.springtraining.domain.OrderForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nested")
public class NestedValidationDemoController {

  // フォーム画面表示用
  @GetMapping
  public String form() {
    return "nested-form";
  }

  // 送信処理用
  @PostMapping
  public String submit(@Valid OrderForm form, Model model) { // @Valid をつけることによって、OrderForm内が検証される。
    // バリデーションで違反がなければここに到達
    model.addAttribute("saved", form);
    return "nested-result";
  }
}

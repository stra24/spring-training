package com.example.springtraining.controller;

import com.example.springtraining.domain.UserForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/complicated")
@Validated
public class ComplicatedValidationDemoController {
  @GetMapping
  public String form(Model model) {
    model.addAttribute("form", new UserForm());
    return "complicated-form";
  }

  // @Valid により UserForm内のフィールドが、本メソッド実行前に検証される。
  @PostMapping
  public String submit(@Valid UserForm form, Model model) {
    // 検証した結果、違反がなければここに到達する（違反時はデフォルトで 500エラーとなる）
    model.addAttribute("saved", form);
    return "complicated-result";
  }
}

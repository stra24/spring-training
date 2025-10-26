package com.example.springtraining.controller;

import com.example.springtraining.domain.UserForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequiredArgsConstructor // @RequiredArgsConstructorをつけることでフィールドvalidatorを引数に持つコンストラクタが定義される。
@RequestMapping("/validator")
public class ValidatorDemoController {

  private final Validator validator; // コンストラクタインジェクションにより、DIコンテナで管理されているBeanが注入されている。

  @GetMapping
  public String page() {
    return "validator";
  }

  // 全体を検証する
  @PostMapping("/check")
  public String check(@ModelAttribute UserForm form, Model model) {
    // 好きなタイミングで手動検証
    Set<ConstraintViolation<UserForm>> violations = validator.validate(form);

    if (violations.isEmpty()) {
      model.addAttribute("result",
          "OK: " + form.getName() + " / " + form.getEmail() + " / " + form.getAge());
    } else {
      var messages = violations.stream()
          .map(ConstraintViolation::getMessage) // 検証がNGだった項目のエラーメッセージを取得する
          .toList();
      model.addAttribute("errors", messages);
    }
    return "validator";
  }

  // name だけ検証する
  @PostMapping("/check-name-only")
  public String checkNameOnly(@ModelAttribute UserForm form, Model model) {
    // 好きなタイミングで手動検証
    var messages = validator.validateProperty(form, "name").stream()
        .map(ConstraintViolation::getMessage)
        .toList();
    if (messages.isEmpty()) {
      model.addAttribute("result", "name はOKです");
    } else {
      model.addAttribute("errors", messages);
    }
    return "validator";
  }
}
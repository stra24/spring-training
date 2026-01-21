package com.example.springtraining.controller;

import com.example.springtraining.domain.UserForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor // @RequiredArgsConstructorをつけることでフィールドvalidatorを引数に持つコンストラクタが定義される。
@RequestMapping("/validation/validator")
public class ValidatorController {

  // コンストラクタインジェクションにより、DIコンテナで管理されているBeanが注入されている。
  private final Validator validator;

  @GetMapping
  public String page() {
    return "validation/validator/form";
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
    return "validation/validator/form";
  }

  // name だけ検証する
  @PostMapping("/check-name-only")
  public String checkNameOnly(@ModelAttribute UserForm form, Model model) {
    // 好きなタイミングで手動検証
    var messages = validator.validateProperty(form, "name").stream()
        .map(ConstraintViolation::getMessage)
        .toList();
    if (messages.isEmpty()) {
      model.addAttribute("result", "nameはOKです");
    } else {
      model.addAttribute("errors", messages);
    }
    return "validation/validator/form";
  }
}
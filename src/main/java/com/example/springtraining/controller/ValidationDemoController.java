package com.example.springtraining.controller;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/check")
@Validated // ← これをつけることで、このクラスの public メソッドに入る直前で、単純型の引数チェックが有効になる
public class ValidationDemoController {

  /**
   * 文字列の必須チェックの挙動確認（@RequestParam × @NotBlank）
   * リクエスト例: GET /check/name?value=Alice
   */
  @GetMapping("/name")
  public String name(
      @NotBlank @RequestParam String value,
      Model model
  ) {
    model.addAttribute("name", value);
    return "simple-validation";
  }

  /**
   * 数値の範囲チェックの挙動確認（@RequestParam × @Min/@Max）
   * リクエスト例: GET /check/age?value=20
   */
  @GetMapping("/age")
  public String age(
      @Min(0) @Max(150) @RequestParam int value,
      Model model
  ) {
    model.addAttribute("age", value);
    return "simple-validation";
  }

  /**
   * 正数チェックの挙動確認（@PathVariable × @Positive）
   * リクエスト例: GET /check/items/42
   */
  @GetMapping("/items/{id}")
  public String item(
      @Positive @PathVariable long id,
      Model model
  ) {
    model.addAttribute("id", id);
    return "simple-validation";
  }

  /**
   * フォームと結果を1画面にまとめたテンプレートを表示
   * リクエスト例: GET /check
   */
  @GetMapping
  public String page() {
    return "simple-validation";
  }
}
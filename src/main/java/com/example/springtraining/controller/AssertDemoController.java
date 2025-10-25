package com.example.springtraining.controller;

import com.example.springtraining.domain.ConsentForm;
import com.example.springtraining.domain.ProfileForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asserts")
@RequiredArgsConstructor
public class AssertDemoController {

  // フォーム画面表示
  @GetMapping
  public String lab() {
    return "asserts";
  }

  // ① boolean型のフィールドに @AssertTrue / @AssertFalse をつけるパターン
  @PostMapping("/consent")
  public String consent(
      @Valid @ModelAttribute("form") ConsentForm form,
      Model model
  ) {
    model.addAttribute("result", "① フィールド検証：OK でした。");
    model.addAttribute("active", "consent");
    return "asserts";
  }

  // ② 引数なし & 戻り値の型がbooleanであるメソッドに @AssertTrue をつけるパターン
  @PostMapping("/profile")
  public String profile(
      @Valid @ModelAttribute("form") ProfileForm form,
      Model model
  ) {
    model.addAttribute("result", "② メソッド検証（20以上または未入力）：OK でした。");
    model.addAttribute("active", "profile");
    return "asserts";
  }
}
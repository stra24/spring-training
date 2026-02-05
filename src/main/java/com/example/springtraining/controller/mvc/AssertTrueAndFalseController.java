package com.example.springtraining.controller.mvc;

import com.example.springtraining.domain.ConsentForm;
import com.example.springtraining.domain.ProfileForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validation/assert")
@RequiredArgsConstructor
public class AssertTrueAndFalseController {

  // フォーム画面表示
  @GetMapping
  public String lab() {
    return "validation/assert/form";
  }

  // ① boolean型のフィールドに @AssertTrue / @AssertFalse をつけるパターン
  @PostMapping("/consent")
  public String consent(
      @Valid @ModelAttribute("form") ConsentForm form,
      Model model
  ) {
    model.addAttribute("result", "① フィールド検証：OK でした。");
    model.addAttribute("active", "consent");
    return "validation/assert/form";
  }

  // ② 引数なし & 戻り値の型がbooleanであるメソッドに @AssertTrue をつけるパターン
  @PostMapping("/profile")
  public String profile(
      @Valid @ModelAttribute("form") ProfileForm form,
      Model model
  ) {
    model.addAttribute("result", "② メソッド検証（20以上または未入力）：OK でした。");
    model.addAttribute("active", "profile");
    return "validation/assert/form";
  }
}
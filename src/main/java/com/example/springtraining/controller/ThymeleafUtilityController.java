package com.example.springtraining.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafUtilityController {

  @GetMapping("/null-demo")
  public String nullDemo(Model model) {
    // ▼文字列まわり
    model.addAttribute("nicknameNull", null);   // null
    model.addAttribute("nicknameEmpty", "");    // 空文字

    // ▼メソッド呼び出しに対する安全呼び出し ?. の確認用
    model.addAttribute("greetingNull", null); // null に対して length() を安全に呼びたい

    // ▼空のコレクションフレームワーク
    model.addAttribute("emptyList", List.of());  // 空リスト
    model.addAttribute("emptyMap", Map.of());    // 空マップ
    model.addAttribute("emptySet", Set.of());    // 空セット

    return "utility/null-demo";
  }

  @GetMapping("/utility")
  public String utility(Model model) {
    // ▼日付・時刻
    model.addAttribute("ldt", LocalDateTime.of(2025, 8, 16, 22, 17, 33));
    model.addAttribute("ld", LocalDate.of(2025, 8, 16));
    model.addAttribute("lt", LocalTime.of(9, 5, 7, 123_000_000));

    // ▼数値
    model.addAttribute("num", 7);

    // ▼文字列
    model.addAttribute("upperSrc", "hello thymeleaf");
    model.addAttribute("lowerSrc", "SAYONARA SPRING");
    model.addAttribute("longText", "Thymeleaf utilities are handy for view-level formatting.");

    return "utility/utility";
  }
}
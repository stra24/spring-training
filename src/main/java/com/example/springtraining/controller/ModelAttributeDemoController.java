package com.example.springtraining.controller;


import com.example.springtraining.domain.RegisterForm;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ma-demo")
public class ModelAttributeDemoController {

  /**
   * ハンドラーメソッドの前に呼ばれるメソッド。
   *
   * ・戻り値あり
   * ・`@ModelAttribute("roles")`で名前を指定している
   * のため、
   *
   * ・キー名: "roles"
   * ・値: このメソッドの戻り値
   * がModelに登録される。
   */
  @ModelAttribute("roles")
  public List<String> roles() {
    return List.of("一般ユーザー", "管理者", "ゲスト");
  }

  /**
   * ハンドラーメソッドの前に呼ばれるメソッド。
   *
   * ・戻り値あり
   * ・`@ModelAttribute`で名前を指定していない
   * のため、
   *
   * ・キー名: registerForm（戻り値の型であるRegisterFormのローワーキャメルケース）
   * ・値: このメソッドの戻り値
   * がModelに登録される。
   */
  @ModelAttribute
  public RegisterForm form() {
    return new RegisterForm();
  }

  /**
   * ハンドラーメソッドの前に呼ばれるメソッド。
   *
   * 戻り値なしのため、
   * model.addAttributeで指定したキーと値がModelに登録される。
   */
  @ModelAttribute
  public void common(Model model) {
    model.addAttribute("appName", "@ModelAttributeの動作確認");
    model.addAttribute("today", LocalDate.now());
  }

  /**
   * 一覧およびフォーム画面。
   */
  @GetMapping
  public String index() {
    return "ma-demo";
  }

  /**
   * フォームの送信結果画面。
   */
  @PostMapping
  public String submit(@ModelAttribute RegisterForm form) {
    return "ma-demo-result";
  }
}

package com.example.springtraining.controller;

import com.example.springtraining.domain.UserForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validation/binding-result")
public class BindingResultController {

  // フォーム画面表示
  // 引数の form は、メソッド内では未使用だが、引数で受け取ることで、Modelの中に（キー名：userFormで）追加される（@ModelAttributeのレクチャーを参照）
  @GetMapping
  public String index(UserForm form, Model model) {
    model.addAttribute("subtitle", "@Valid の検証結果を BindingResult で画面に表示します");
    return "validation/binding-result/form";
  }

  // 送信処理
  @PostMapping
  public String submit(
      @Valid UserForm form,
      BindingResult br, // 検証したいオブジェクト の「直後」に BindingResult を指定すること。
      Model model
  ) {
    if (br.hasErrors()) {
      model.addAttribute("subtitle", "検証が失敗しました");
      return "validation/binding-result/form";
    }

    // 検証成功時の処理
    model.addAttribute("subtitle", "検証が成功しました");
    model.addAttribute(
        "line",
        String.format(
            "name=%s, email=%s, age=%s",
            form.getName(),
            form.getEmail(),
            form.getAge()
        )
    );
    return "validation/binding-result/form";
  }
}
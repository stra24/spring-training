package com.example.springtraining.controller;

import com.example.springtraining.domain.UserForm;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/binding-result")
public class BindingResultDemoController {

  // フォーム画面表示
  // 引数の form は、メソッド内では未使用だが、引数で受け取ることで、Modelの中に（キー名：userFormで）追加される（@ModelAttributeのレクチャーを参照）
  @GetMapping
  public String index(UserForm form, Model model) {
    model.addAttribute("subtitle", "@Valid の検証結果を BindingResult で画面に表示します");
    return "binding-result";
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
      return "binding-result";
    }

    // 検証成功時の処理
    model.addAttribute("subtitle", "検証が成功しました");
    model.addAttribute(
        "lines",
        List.of(
            "処理区分: SUBMIT",
            String.format(
                "name=%s, email=%s, age=%s",
                form.getName(),
                form.getEmail(),
                form.getAge()
            )
        )
    );
    return "binding-result";
  }
}

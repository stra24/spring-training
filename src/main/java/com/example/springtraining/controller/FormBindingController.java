package com.example.springtraining.controller;

import com.example.springtraining.domain.Dog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormBindingController {

  /**
   * フォーム画面を表示する。
   *
   * @param model Model
   * @return フォーム画面のテンプレート名
   */
  @GetMapping("/binding")
  public String showDogForm(Model model) {
    model.addAttribute(
        "dog",
        new Dog(null, null)
    );
    return "binding/dog-form";
  }

  /**
   * フォームの送信結果画面を表示する。
   *
   * @param model Model
   * @return フォームの送信結果画面のテンプレート名
   */
  @PostMapping("/binding")
  public String submitDogForm(@ModelAttribute Dog dog, Model model) {
    model.addAttribute("submittedDog", dog);
    return "binding/dog-result";
  }
}
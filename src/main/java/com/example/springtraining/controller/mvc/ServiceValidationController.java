package com.example.springtraining.controller.mvc;

import com.example.springtraining.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/validation/service")
public class ServiceValidationController {

  private final AccountService accountService;

  @GetMapping
  public String page() {
    return "validation/service/form";
  }

  @PostMapping("/register")
  public String register(
      @RequestParam String name,
      @RequestParam String email,
      @RequestParam int age,
      Model model
  ) {
    accountService.register(name, email, age); // ← メソッドの呼び出し時に引数検証
    model.addAttribute(
        "result",
        "① 引数の検証：OK でした。name：" + name + ", email：" + email + ", age：" + age
    );
    return "validation/service/form";
  }

  @PostMapping("/load")
  public String load(
      @RequestParam String id,
      Model model
  ) {
    var dto = accountService.load(id); // ← メソッドの呼び出し時に戻り値検証
    model.addAttribute(
        "result",
        "② 戻り値の検証：OK でした。id：" + dto.getId()
    );
    return "validation/service/form";
  }
}
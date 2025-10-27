package com.example.springtraining.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/error-demo")
public class ErrorDemoController {

  @GetMapping
  public String index() {
    return "error-demo";
  }

  // 1) ControllerのExceptionHandlerで拾わせる用。IllegalArgumentException を投げる。
  @PostMapping("/iae")
  public String throwIae() {
    throw new IllegalArgumentException("不正なパラメータ（デモ）");
  }

  // 2) @ControllerAdviceクラスのExceptionHandlerで拾わせる用。NullPointerException を投げる。
  @PostMapping("/npe")
  public String throwNpe() {
    String s = null;
    s.length(); // NullPointerException
    return "redirect:/error-demo";
  }

  // 4) error.html を表示させる用: どこにも拾われない RuntimeException を投げる。
  @PostMapping("/re")
  public String throwRe() {
    throw new RuntimeException("RuntimeException（デモ: error.htmlの挙動確認）");
  }

  // このController内で発生した IllegalArgumentException を処理する。
  @ExceptionHandler(IllegalArgumentException.class)
  public String handleIae(IllegalArgumentException ex, Model model, HttpServletRequest req) {
    model.addAttribute("handledBy", "Controller @ExceptionHandler");
    model.addAttribute("errorViewId", "templates/error/custom-error.html");
    model.addAttribute("exception", ex.getClass().getSimpleName());
    model.addAttribute("path", req.getRequestURI());
    return "error/custom-error";
  }
}

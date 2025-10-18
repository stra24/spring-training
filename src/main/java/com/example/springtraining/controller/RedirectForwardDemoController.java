package com.example.springtraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RedirectForwardDemoController {

  // 名前リスト（DBの代わりとして、簡易的に用意）
  private static final List<String> NAMES = new ArrayList<>();

  // フォーム画面表示
  @GetMapping("/redirect-forward/form")
  public String showFormGet(
      @RequestParam(name = "name", required = false) String name,
      Model model
  ) {
    if (name != null && !name.isBlank()) {
      model.addAttribute("name", name);
    }
    return "redirect-forward-form";
  }

  // フォワード時の受け口（POSTでもフォーム画面を描画できるようにしておく）
  @PostMapping("/redirect-forward/form")
  public String showFormPost() {
    return "redirect-forward-form";
  }

  // ユーザー登録：未入力→フォワード、成功→リダイレクト（PRG）
  @PostMapping("/redirect-forward/register")
  public String register(
      @RequestParam(name = "name", required = false) String name,
      Model model,
      RedirectAttributes ra
  ) {

    // 入力チェック（未入力なら登録フォーム画面へフォワード）
    if (name == null || name.isBlank()) {
      model.addAttribute("error", "お名前を入力してください");
      model.addAttribute("name", "");
      return "forward:/redirect-forward/form"; // URLは /redirect-forward/register のまま
    }

    // 名前リストに追加
    NAMES.add(name);

    // 完了メッセージ（リダイレクト先に渡す）
    ra.addFlashAttribute("message", "「" + name + "」さんを登録しました。");

    // 一覧画面へリダイレクト
    return "redirect:/redirect-forward/list";
  }

  // 一覧画面表示
  @GetMapping("/redirect-forward/list")
  public String list(Model model) {
    model.addAttribute("names", NAMES);
    model.addAttribute("count", NAMES.size());
    return "redirect-forward-list";
  }

  // 名前リストをクリア
  @PostMapping("/redirect-forward/clear")
  public String clear(RedirectAttributes ra) {
    NAMES.clear();
    ra.addFlashAttribute("message", "一覧をクリアしました。");

    // 一覧画面へリダイレクト
    return "redirect:/redirect-forward/list";
  }
}
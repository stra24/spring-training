package com.example.springtraining.controller;

import com.example.springtraining.domain.article.ArticleForm;
import com.example.springtraining.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ArticleController {

  private final ArticleService service;

  // 一覧表示
  @GetMapping("/articles")
  public String list(Model model) {
    model.addAttribute("articles", service.listOrderedByUpdatedAtDesc()); // listAll() → listOrderedByUpdatedAtDesc()に修正。
    return "article-list";
  }

  // 詳細表示
  @GetMapping("/articles/{id}")
  public String detail(@PathVariable Long id, Model model) {
    model.addAttribute("article", service.get(id));
    return "article-detail";
  }

  // 新規作成フォーム画面の表示
  @GetMapping("/articles/new")
  public String newArticle(Model model) {
    model.addAttribute("articleForm", new ArticleForm());
    return "article-new";
  }

  // 新規作成
  @PostMapping("/articles")
  public String create(@ModelAttribute("articleForm") ArticleForm form) {
    service.create(form);
    return "redirect:/articles";
  }
}
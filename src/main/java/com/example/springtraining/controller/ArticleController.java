package com.example.springtraining.controller;

import com.example.springtraining.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class ArticleController {

  private final ArticleService articleService;

  // 一覧表示
  @GetMapping("/articles")
  public String showArticles(Model model) {
    model.addAttribute("articles", articleService.getArticles());
    return "jdbc/article/list";
  }

  // 詳細表示
  @GetMapping("/articles/{id}")
  public String showArticle(@PathVariable Long id, Model model) {
    model.addAttribute("article", articleService.getArticle(id));
    return "jdbc/article/detail";
  }
}
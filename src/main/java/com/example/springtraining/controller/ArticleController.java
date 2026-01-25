package com.example.springtraining.controller;

import com.example.springtraining.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ArticleController {

  private final ArticleService articleService;

  @GetMapping("/articles")
  public String getArticles(Model model) {
    model.addAttribute("articles", articleService.listAll());
    return "jdbc/article/list";
  }
}
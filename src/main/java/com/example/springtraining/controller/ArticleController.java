package com.example.springtraining.controller;

import com.example.springtraining.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ArticleController {

  private final ArticleService service;

  @GetMapping("/articles")
  public String list(Model model) {
    model.addAttribute("articles", service.listAll());
    return "article-list";
  }
}


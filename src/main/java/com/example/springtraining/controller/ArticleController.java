package com.example.springtraining.controller;

import com.example.springtraining.domain.article.Article;
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

  // 編集画面の表示
  @GetMapping("/articles/{id}/edit")
  public String edit(@PathVariable Long id, Model model) {
    Article article = service.get(id);

    // 既存記事の値をフォーム用オブジェクトに詰め替える
    ArticleForm form = new ArticleForm();
    form.setTitle(article.getTitle());
    form.setContent(article.getContent());

    model.addAttribute("articleForm", form);
    model.addAttribute("articleId", id); // actionで使う。
    return "article-edit";
  }

  // 更新
  @PostMapping("/articles/{id}")
  public String update(
      @PathVariable Long id,
      @ModelAttribute("articleForm") ArticleForm form
  ) {
    service.update(id, form);
    return "redirect:/articles/" + id;
  }

  // 削除
  @PostMapping("/articles/{id}/delete")
  public String delete(@PathVariable Long id) {
    service.delete(id);
    return "redirect:/articles";
  }
}
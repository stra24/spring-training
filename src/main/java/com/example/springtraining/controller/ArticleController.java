package com.example.springtraining.controller;

import com.example.springtraining.domain.dto.ArticleDto;
import com.example.springtraining.domain.form.ArticleForm;
import com.example.springtraining.service.ArticleService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ArticleController {

  private final ArticleService articleService;

  // 一覧表示
  @GetMapping("/articles")
  public String showArticles(
      @Nullable @RequestParam(required = false) String keyword,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      Model model
  ) {
    Page<ArticleDto> articlePage = articleService.searchArticlePageByCondition(keyword, page, size);

    model.addAttribute("articlePage", articlePage);
    model.addAttribute("currentPageNum", page);
    model.addAttribute("pageSize", size);
    model.addAttribute("totalPageNum", articlePage.getTotalPages());
    return "jdbc/article/list";
  }

  // 詳細表示
  @GetMapping("/articles/{id}")
  public String showArticle(@PathVariable Long id, Model model) {
    model.addAttribute("article", articleService.getArticle(id));
    return "jdbc/article/detail";
  }

  // 新規作成フォーム画面の表示
  @GetMapping("/articles/new")
  public String showArticleCreateForm(Model model) {
    model.addAttribute("articleForm", new ArticleForm(null, null));
    return "jdbc/article/new";
  }

  // 新規作成
  @PostMapping("/articles")
  public String createArticle(@ModelAttribute("articleForm") ArticleForm form) {
    articleService.createArticle(form);
    return "redirect:/articles";
  }

  // 編集画面の表示
  @GetMapping("/articles/{id}/edit")
  public String showArticleEditForm(@PathVariable Long id, Model model) {
    ArticleDto articleDto = articleService.getArticle(id);

    // 既存記事の値をフォーム用オブジェクトに詰め替える
    ArticleForm form = new ArticleForm(
        articleDto.getTitle(),
        articleDto.getContent()
    );

    model.addAttribute("articleForm", form);
    model.addAttribute("articleId", id); // actionで使う。
    return "jdbc/article/edit";
  }

  // 更新
  @PostMapping("/articles/{id}")
  public String updateArticle(
      @PathVariable Long id,
      @ModelAttribute("articleForm") ArticleForm form
  ) {
    articleService.updateArticle(id, form);
    return "redirect:/articles/" + id;
  }

  // 削除
  @PostMapping("/articles/{id}/delete")
  public String deleteArticle(@PathVariable Long id) {
    articleService.deleteArticle(id);
    return "redirect:/articles";
  }
}
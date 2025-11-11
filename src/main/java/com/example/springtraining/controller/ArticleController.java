package com.example.springtraining.controller;

import com.example.springtraining.domain.article.Article;
import com.example.springtraining.domain.article.ArticleForm;
import com.example.springtraining.domain.article.CommentForm;
import com.example.springtraining.service.ArticleService;
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

  private final ArticleService service;

  // 一覧表示
  @GetMapping("/articles")
  public String list(Model model) {
    model.addAttribute("articles", service.listOrderedByUpdatedAtDesc()); // listAll() → listOrderedByUpdatedAtDesc()に修正。
    return "article-list";
  }

  // 詳細画面（コメント一覧＋コメントフォーム含む）の表示
  @GetMapping("/articles/{id}")
  public String detail(@PathVariable Long id, Model model) {
    Article article = service.get(id);
    model.addAttribute("article", article);
    model.addAttribute("commentForm", new CommentForm());
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

  // タイトルの部分一致検索
  @GetMapping("/articles/search")
  public String search(@RequestParam String keyword, Model model) {
    model.addAttribute("articles", service.searchByTitleKeyword(keyword));
    model.addAttribute("keyword", keyword);
    return "article-list";
  }

  // ページ付き一覧取得
  @GetMapping("/articles/page")
  public String pagedList(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "5") int size,
      Model model
  ) {
    Page<Article> articlePage = service.getPage(page, size);

    model.addAttribute("articlePage", articlePage);
    model.addAttribute("currentPage", page);
    model.addAttribute("pageSize", size);
    model.addAttribute("totalPages", articlePage.getTotalPages());

    return "article-page";
  }

  // コメントを追加して、記事詳細画面にリダイレクト
  @PostMapping("/articles/{id}/comments")
  public String addComment(
      @PathVariable Long id,
      @ModelAttribute("commentForm") CommentForm form
  ) {
    service.addCommentByArticle(id, form);
    return "redirect:/articles/" + id;
  }

  // コメントを追加して、記事詳細画面にリダイレクト（内部でロールバックされる）
  @PostMapping("/articles/{id}/comments/rollback")
  public String addCommentAndRollback(
      @PathVariable Long id,
      @ModelAttribute("commentForm") CommentForm form
  ) {
    service.addCommentAndThrowException(id, form);
    return "redirect:/articles/" + id;
  }
}
package com.example.springtraining.controller;

import com.example.springtraining.controller.dto.ArticleResponse;
import com.example.springtraining.domain.article.Article;
import com.example.springtraining.service.ArticleService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
public class ArticleApiController {

  private final ArticleService articleService;

  /**
   * 記事一覧を取得するAPI。
   */
  @GetMapping
  public List<ArticleResponse> list() {
    List<Article> articles = articleService.listOrderedByUpdatedAtDesc();
    return articles.stream()
        .map(ArticleResponse::from) // Article → ArticleResponse への変換
        .toList();
  }

  /**
   * 記事詳細を取得するAPI。
   * 存在しないIDの場合は ArticleNotFoundException がスローされ、404 Not Found が返る。
   */
  @GetMapping("/{id}")
  public ArticleResponse detail(@PathVariable Long id) {
    Article article = articleService.get(id);
    return ArticleResponse.from(article); // Article → ArticleResponse への変換
  }
}


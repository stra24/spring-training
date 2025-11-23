package com.example.springtraining.controller;

import com.example.springtraining.controller.dto.ArticleCreateRequest;
import com.example.springtraining.controller.dto.ArticleResponse;
import com.example.springtraining.controller.dto.ArticleUpdateRequest;
import com.example.springtraining.domain.article.Article;
import com.example.springtraining.service.ArticleService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
  public ResponseEntity<List<ArticleResponse>> list() {
    List<ArticleResponse> articles = articleService.listOrderedByUpdatedAtDesc().stream()
        .map(ArticleResponse::from) // Article → ArticleResponse への変換
        .toList();
    return ResponseEntity.ok(articles);
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

  /**
   * 記事を新規登録するAPI。
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ArticleResponse create(@RequestBody ArticleCreateRequest request) {

    // 1. リクエストDTO → ArticleForm に変換
    var form = request.toForm();

    // 2. Service経由で記事を作成（作成されたArticleを受け取る）
    Article created = articleService.create(form);

    // 3. Article → ArticleResponse に変換して返す
    return ArticleResponse.from(created);
  }

  /**
   * 記事を更新するAPI。
   */
  @PutMapping("/{id}")
  public ArticleResponse update(
      @PathVariable Long id,
      @RequestBody ArticleUpdateRequest request
  ) {
    // 1. リクエストDTO → ArticleForm に変換
    var form = request.toForm();

    // 2. Service経由で記事を更新（更新後のArticleを受け取る）
    Article updated = articleService.update(id, form);

    // 3. Article → ArticleResponse に変換して返す
    return ArticleResponse.from(updated);
  }

  /**
   * 記事を削除するAPI。
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    // 存在しない場合は ArticleNotFoundException がスローされ、404 Not Found が返る
    articleService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/error-demo")
  public ArticleResponse errorDemo() {
    throw new RuntimeException("テスト用の想定外エラーです");
  }
}


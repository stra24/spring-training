package com.example.springtraining.controller;

import com.example.springtraining.client.ArticleApiClient;
import com.example.springtraining.controller.dto.ArticleCreateRequest;
import com.example.springtraining.controller.dto.ArticleResponse;
import com.example.springtraining.controller.dto.ArticleUpdateRequest;
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

/**
 * RestTemplate の動作確認用のデモController。
 *
 * /resttemplate-demo/... にアクセスすると、
 * 自分自身の /api/articles を RestTemplate 経由で叩いて、その結果を返す。
 */
@RestController
@RequestMapping("/resttemplate-demo")
@AllArgsConstructor
public class RestTemplateDemoController {

  private final ArticleApiClient articleApiClient;

  /**
   * getForObject のデモ。
   * RestTemplate で GET /api/articles を叩いて一覧を取得する。
   */
  @GetMapping("/articles")
  public List<ArticleResponse> listArticles() {
    return articleApiClient.getArticles();
  }

  /**
   * getForEntity のデモ。
   * RestTemplate で GET /api/articles/{id} を叩いて詳細を取得する。
   *
   * ResponseEntity からステータスコードなどもログに出してみる。
   */
  @GetMapping("/articles/{id}")
  public ArticleResponse getArticle(@PathVariable Long id) {
    ResponseEntity<ArticleResponse> response = articleApiClient.getArticleEntity(id);

    // 学習用として、ステータスコードやヘッダを標準出力に出してみる。
    System.out.println("StatusCode = " + response.getStatusCode());
    System.out.println("Headers    = " + response.getHeaders());

    // 実際に呼び出し元に返すのは「レスポンスボディ（ArticleResponse）」だけ。
    return response.getBody();
  }

  /**
   * postForObject のデモ。
   * RestTemplate で POST /api/articles を叩いて記事を登録する。
   */
  @PostMapping("/articles/postForObject")
  public ArticleResponse createArticleWithPostForObject(
      @RequestBody ArticleCreateRequest request
  ) {
    return articleApiClient.createArticleWithPostForObject(request);
  }

  /**
   * postForEntity のデモ。
   * RestTemplate で POST /api/articles を叩いて記事を登録する。
   *
   * ResponseEntity からステータスコードなどもログに出してみる。
   */
  @PostMapping("/articles/postForEntity")
  public ArticleResponse createArticleWithPostForEntity(
      @RequestBody ArticleCreateRequest request
  ) {
    ResponseEntity<ArticleResponse> response =
        articleApiClient.createArticleWithPostForEntity(request);

    // 学習用として、ステータスコードやヘッダを標準出力に出してみる。
    System.out.println("StatusCode = " + response.getStatusCode());
    System.out.println("Headers    = " + response.getHeaders());

    // 実際に呼び出し元に返すのは「レスポンスボディ（ArticleResponse）」だけ。
    return response.getBody();
  }

  /**
   * exchange を使った PUT のデモ。
   * RestTemplate 経由で PUT /api/articles/{id} を叩いて記事を更新する。
   */
  @PutMapping("/articles/{id}")
  public ArticleResponse updateArticle(
      @PathVariable Long id,
      @RequestBody ArticleUpdateRequest request
  ) {
    return articleApiClient.updateArticle(id, request);
  }

  /**
   * exchange を使った DELETE のデモ。
   * RestTemplate 経由で DELETE /api/articles/{id} を叩いて記事を削除する。
   */
  @DeleteMapping("/articles/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteArticle(@PathVariable Long id) {
    articleApiClient.deleteArticle(id);
  }
}
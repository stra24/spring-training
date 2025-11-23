package com.example.springtraining.controller;

import com.example.springtraining.client.ArticleApiClient;
import com.example.springtraining.controller.dto.ArticleResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // 学習用として、ステータスコードやヘッダを標準出力に出してみる
    System.out.println("StatusCode = " + response.getStatusCode());
    System.out.println("Headers    = " + response.getHeaders());

    // 実際に呼び出し元に返すのは「レスポンスボディ（ArticleResponse）」だけ。
    return response.getBody();
  }
}
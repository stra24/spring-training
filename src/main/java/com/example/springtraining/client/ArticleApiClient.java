package com.example.springtraining.client;

import com.example.springtraining.controller.dto.ArticleCreateRequest;
import com.example.springtraining.controller.dto.ArticleResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * /api/articles 系のREST APIを呼び出すためのクライアント。
 */
@Component
@RequiredArgsConstructor
public class ArticleApiClient {

  private final RestTemplate restTemplate;

  // application.yml からベースURLを注入する
  @Value("${external-api.base-url}")
  private String baseUrl;

  /**
   * 記事一覧を取得する（GET /api/articles）。
   * getForObject を使って「レスポンスボディだけ」を受け取る例。
   */
  public List<ArticleResponse> getArticles() {
    String url = baseUrl + "/api/articles";
    ArticleResponse[] array = restTemplate.getForObject(url, ArticleResponse[].class);

    if (array == null) {
      return Collections.emptyList();
    }

    return Arrays.asList(array);
  }

  /**
   * 記事詳細を取得する（GET /api/articles/{id}）。
   * getForEntity を使って ResponseEntity を受け取る例。
   */
  public ResponseEntity<ArticleResponse> getArticleEntity(Long id) {
    String url = baseUrl + "/api/articles/" + id;
    return restTemplate.getForEntity(url, ArticleResponse.class);
  }

  /**
   * 記事を登録する（POST /api/articles）。
   * postForObject を使って、レスポンスボディ（ArticleResponse）だけを受け取る例。
   */
  public ArticleResponse createArticleWithPostForObject(ArticleCreateRequest request) {
    String url = baseUrl + "/api/articles";
    return restTemplate.postForObject(url, request, ArticleResponse.class);
  }

  /**
   * 記事を登録する（POST /api/articles）。
   * postForEntity を使って、レスポンスボディ（ArticleResponse）だけでなく、レスポンスステータスコードやレスポンスヘッダも含めて受け取る例。
   */
  public ResponseEntity<ArticleResponse> createArticleWithPostForEntity(ArticleCreateRequest request) {
    String url = baseUrl + "/api/articles";
    return restTemplate.postForEntity(url, request, ArticleResponse.class);
  }
}

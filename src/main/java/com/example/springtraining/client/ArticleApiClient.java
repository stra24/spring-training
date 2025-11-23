package com.example.springtraining.client;

import com.example.springtraining.controller.dto.ArticleCreateRequest;
import com.example.springtraining.controller.dto.ArticleResponse;
import com.example.springtraining.controller.dto.ArticleUpdateRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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

  /**
   * 記事を更新する（PUT /api/articles/{id}）。
   * exchange を使って PUT を実行し、
   * 404 のときは ResponseStatusException(404) に変換して投げ直す。
   */
  public ArticleResponse updateArticle(Long id, ArticleUpdateRequest request) {
    String url = baseUrl + "/api/articles/" + id;

    // ヘッダーを設定（JSONを送るため Content-Type を application/json に）
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    // ボディ + ヘッダー を HttpEntity にまとめる
    HttpEntity<ArticleUpdateRequest> httpEntity =
        new HttpEntity<>(request, headers);

    try {
      ResponseEntity<ArticleResponse> response = restTemplate.exchange(
          url,
          HttpMethod.PUT,
          httpEntity,
          ArticleResponse.class
      );

      return response.getBody();

    } catch (HttpClientErrorException.NotFound e) {
      // RestTemplate が受け取った 404 を、呼び出し元でも ステータスコード:404 の例外 として返したいので、
      // ResponseStatusException(404) に変換して投げ直す。
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "記事が見つかりませんでした id=" + id,
          e
      );
    }
  }

  /**
   * 記事を削除する（DELETE /api/articles/{id}）。
   * exchange を使って DELETE を実行し、
   * 404 のときは ResponseStatusException(404) に変換して投げ直す。
   */
  public void deleteArticle(Long id) {
    String url = baseUrl + "/api/articles/" + id;

    try {
      restTemplate.exchange(
          url,
          HttpMethod.DELETE,
          null, // DELETE の場合、通常ボディは不要なのでnullを指定。
          Void.class
      );

    } catch (HttpClientErrorException.NotFound e) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "記事が見つかりませんでした id=" + id,
          e
      );
    }
  }
}

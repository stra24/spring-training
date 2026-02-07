package com.example.springtraining.client;

import com.example.springtraining.domain.dto.ArticleDetailDto;
import com.example.springtraining.domain.dto.ArticleDto;
import com.example.springtraining.domain.request.ArticleCreateRequest;
import com.example.springtraining.domain.request.ArticleUpdateRequest;
import jakarta.annotation.Nullable;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 記事のAPIを呼び出すためのクライアント。
 */
@Component
@RequiredArgsConstructor
public class ArticleApiClient {

  private final RestTemplate restTemplate;

  // application.yaml からベースURLを注入する
  @Value("${external-api.base-url}")
  private String baseUrl;

  /**
   * 記事一覧を取得する（GET /api/articles）。getForObject を使って「レスポンスボディだけ」を受け取る例。
   */
  public List<ArticleDto> getArticles(@Nullable String keyword) {
    URI uri = UriComponentsBuilder
        .fromUriString(baseUrl)
        .path("/api/articles")
        .queryParamIfPresent(
            "keyword",
            (keyword == null || keyword.isBlank())
                ? Optional.empty()
                : Optional.of(keyword)
        )
        .build(true)
        .toUri();

    // GET /api/articles/{id} のAPIを実行して、そのレスポンス全体を受け取る。
    ArticleDto[] array = restTemplate.getForObject(uri, ArticleDto[].class);
    return array == null ? Collections.emptyList() : Arrays.asList(array);
  }

  /**
   * 記事詳細を取得する（GET /api/articles/{id}）。getForEntity を使って ResponseEntity を受け取る例。
   */
  public ResponseEntity<ArticleDetailDto> getArticle(Long id) {
    String url = baseUrl + "/api/articles/" + id;

    // GET /api/articles/{id} のAPIを実行して、そのレスポンス全体を受け取る。
    return restTemplate.getForEntity(url, ArticleDetailDto.class);
  }

  /**
   * 記事を登録する（POST /api/articles）。postForObject を使って、レスポンスボディだけを受け取る例。
   */
  public ArticleDetailDto createArticleByPostForObject(
      ArticleCreateRequest request
  ) {
    String url = baseUrl + "/api/articles";
    return restTemplate.postForObject(url, request, ArticleDetailDto.class);
  }

  /**
   * 記事を登録する（POST /api/articles）。postForEntityを使って、レスポンス全体を受け取る例。
   */
  public ResponseEntity<ArticleDetailDto> createArticleByPostForEntity(
      ArticleCreateRequest request
  ) {
    String url = baseUrl + "/api/articles";
    return restTemplate.postForEntity(url, request, ArticleDetailDto.class);
  }

  /**
   * 記事を更新する（PUT /api/articles/{id}）。exchange を使って PUT を実行する。
   */
  public ArticleDetailDto updateArticle(Long id, ArticleUpdateRequest request) {
    String url = baseUrl + "/api/articles/" + id;

    // ヘッダーを設定（JSONを送るため、Content-Type は application/json をセットする）
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    // ボディ + ヘッダー を HttpEntity にまとめる
    HttpEntity<ArticleUpdateRequest> httpEntity =
        new HttpEntity<>(request, headers);

    try {
      ResponseEntity<ArticleDetailDto> response = restTemplate.exchange(
          url,
          HttpMethod.PUT,
          httpEntity,
          ArticleDetailDto.class
      );

      return response.getBody();

    } catch (HttpClientErrorException.NotFound e) {
      // RestTemplate が受け取った 404 を、呼び出し元でも ステータスコード:404 の例外 として返したいので、
      // ResponseStatusException(404) に変換して投げ直す。
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          e.getMessage(),
          e
      );
    }
  }

  /**
   * 記事を削除する（DELETE /api/articles/{id}）。exchange を使って DELETE を実行する。
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
          e.getMessage(),
          e
      );
    }
  }
}

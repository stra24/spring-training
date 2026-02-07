package com.example.springtraining.client;

import com.example.springtraining.domain.dto.ArticleDetailDto;
import com.example.springtraining.domain.dto.ArticleDto;
import jakarta.annotation.Nullable;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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
}

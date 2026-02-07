package com.example.springtraining.controller.api;

import com.example.springtraining.domain.dto.ArticleDetailDto;
import com.example.springtraining.domain.dto.ArticleDto;
import com.example.springtraining.domain.request.ArticleCreateRequest;
import com.example.springtraining.service.ArticleService;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest-template/articles")
public class RestTemplateArticleApiController {

  private final ArticleService articleService;

  // 一覧取得
  @GetMapping
  public List<ArticleDto> getArticles(@Nullable @RequestParam(required = false) String keyword) {
    return articleService.getArticlesByApi(keyword);
  }

  // 詳細取得
  @GetMapping("/{id}")
  public ArticleDetailDto getArticle(@PathVariable Long id) {
    return articleService.getArticleByApi(id);
  }

  // 新規作成（postForObjectを使用）
  @PostMapping("/postForObject")
  @ResponseStatus(HttpStatus.CREATED)
  public ArticleDetailDto createArticleByPostForObject(@RequestBody ArticleCreateRequest request) {
    return articleService.createArticleByPostForObject(request);
  }

  // 新規作成（postForEntityを使用）
  @PostMapping("/postForEntity")
  @ResponseStatus(HttpStatus.CREATED)
  public ArticleDetailDto createArticleByPostForEntity(@RequestBody ArticleCreateRequest request) {
    return articleService.createArticleByPostForEntity(request);
  }
}
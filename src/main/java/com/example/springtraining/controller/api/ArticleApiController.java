package com.example.springtraining.controller.api;

import com.example.springtraining.domain.dto.ArticleDetailDto;
import com.example.springtraining.domain.dto.ArticleDto;
import com.example.springtraining.domain.request.ArticleCreateRequest;
import com.example.springtraining.service.ArticleService;
import com.example.springtraining.service.CommentService;
import com.example.springtraining.service.TagService;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/api/articles")
public class ArticleApiController {

  private final ArticleService articleService;
  private final CommentService commentService;
  private final TagService tagService;

  // 一覧取得
  @GetMapping
  public List<ArticleDto> getArticles(@Nullable @RequestParam(required = false) String keyword) {
    return articleService.searchArticles(keyword);
  }

  // 詳細取得（記事＋コメント）
  @GetMapping("/{id}")
  public ArticleDetailDto getArticle(@PathVariable Long id) {
    return articleService.getArticleDetail(id);
  }

  // 新規作成
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ArticleDetailDto createArticle(@RequestBody ArticleCreateRequest request) {
    return articleService.createArticle(request);
  }
}
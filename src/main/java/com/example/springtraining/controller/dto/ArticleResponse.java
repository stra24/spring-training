package com.example.springtraining.controller.dto;

import com.example.springtraining.domain.article.Article;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleResponse {

  private final Long id;
  private final String title;
  private final String content;

  public static ArticleResponse from(Article article) {
    return new ArticleResponse(
        article.getId(),
        article.getTitle(),
        article.getContent()
    );
  }
}
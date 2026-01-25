package com.example.springtraining.domain.dto;

import com.example.springtraining.domain.entity.Article;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class ArticleDto {

  Long id;
  String title;
  String content;
  LocalDateTime updatedAt;

  public static ArticleDto from(Article a) {
    return new ArticleDto(
        a.getId(),
        a.getTitle(),
        a.getContent(),
        a.getUpdatedAt()
    );
  }
}
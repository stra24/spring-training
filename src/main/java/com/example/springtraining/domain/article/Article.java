package com.example.springtraining.domain.article;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("articles")
public class Article {
  @Id
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long version;
}
package com.example.springtraining.domain.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("comments")
@AllArgsConstructor
public class Comment {

  @Id
  private Long id;
  private Long articleId;
  private String content;
  private LocalDateTime createdAt;

  // 新規作成用
  public static Comment newComment(Long articleId, String content) {
    return new Comment(null, articleId, content, LocalDateTime.now());
  }
}
package com.example.springtraining.domain.article;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("comments")
public class Comment {

  @Id
  private Long id;
  private Long articleId;
  private String content;
  private LocalDateTime createdAt;

  // 新規作成用のコメントを作成する。
  public static Comment newComment(Long articleId, String content) {
    Comment c = new Comment();
    c.setArticleId(articleId);
    c.setContent(content);
    c.setCreatedAt(LocalDateTime.now());
    return c;
  }
}
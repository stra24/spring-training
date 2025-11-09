package com.example.springtraining.domain.article;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
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

  // この記事に紐づくコメントリスト
  @MappedCollection(idColumn = "article_id")
  private Set<Comment> comments;

  // コメントを追加する。
  public void addComment(Comment comment) {
    if (this.comments == null) {
      this.comments = new HashSet<>();
    }
    this.comments.add(comment);
  }
}
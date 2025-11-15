package com.example.springtraining.domain.article;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
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
  @Version
  private Long version;

  // この記事に紐づくコメントリスト
  @MappedCollection(idColumn = "article_id")
  private Set<Comment> comments;

  // この記事に紐づくタグリスト（中間テーブル article_tag）
  @MappedCollection(idColumn = "article_id")
  private Set<ArticleTag> tags = new LinkedHashSet<>();

  // コメントを追加する。
  public void addComment(Comment comment) {
    if (this.comments == null) {
      this.comments = new HashSet<>();
    }
    this.comments.add(comment);
  }

  // 記事に紐づくタグIDリストを返す。
  public Set<Long> getTagIds() {
    return tags.stream()
        .map(ArticleTag::getTagId)
        .collect(Collectors.toSet());
  }

  // 引数で与えられたタグIDリストの内容に洗い替えで更新する。
  public void updateTagsByTagIds(List<Long> tagIds) {
    // 一旦クリアしてから、重複を排除したうえで再構築する。
    this.tags.clear();
    tagIds.stream()
        .distinct()
        .map(ArticleTag::new)
        .forEach(this.tags::add);
  }
}
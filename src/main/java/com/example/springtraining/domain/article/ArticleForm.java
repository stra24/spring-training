package com.example.springtraining.domain.article;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ArticleForm {

  private String title;
  private String content;

  // 新規登録用のArticleを作成して返す。
  public Article toNewArticle() {
    Article article = new Article();
    article.setTitle(this.title);
    article.setContent(this.content);
    article.setVersion(0L);

    // 新規登録なので作成・更新日時は現在日時で揃える。
    LocalDateTime now = LocalDateTime.now();
    article.setCreatedAt(now);
    article.setUpdatedAt(now);

    return article;
  }
}
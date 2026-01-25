package com.example.springtraining.domain.form;

import com.example.springtraining.domain.entity.Article;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ArticleForm {

  private String title;
  private String content;

  // 新規登録用のArticleを作成して返す。
  public Article toNewArticle() {
    // 新規登録なので作成・更新日時は現在日時で揃える。
    LocalDateTime now = LocalDateTime.now();
    return new Article(null, this.title, this.content, now, now, 0L);
  }
}
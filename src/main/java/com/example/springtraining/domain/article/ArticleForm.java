package com.example.springtraining.domain.article;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ArticleForm {

  private String title;
  private String content;
  private List<Long> tagIds = new ArrayList<>();

  // 新規登録用のArticleを作成して返す。
  public Article toNewArticle() {
    Article article = new Article();
    article.setTitle(this.title);
    article.setContent(this.content);
    article.setVersion(null);
    article.updateTagsByTagIds(this.tagIds);

    // 新規登録なので作成・更新日時は現在日時で揃える。
    LocalDateTime now = LocalDateTime.now();
    article.setCreatedAt(now);
    article.setUpdatedAt(now);

    return article;
  }

  // 更新用のArticleを作成して返す。
  public Article toUpdatedArticle(Article currentArticle) {
    Article article = new Article();
    article.setId(currentArticle.getId());
    article.setTitle(this.title);
    article.setContent(this.content);
    article.setVersion(currentArticle.getVersion());
    article.updateTagsByTagIds(this.tagIds);

    // 更新なので createdAt はそのままにして、updatedAt だけ現在日時（更新時の日時）にする。
    article.setCreatedAt(currentArticle.getCreatedAt());
    article.setUpdatedAt(LocalDateTime.now());
    return article;
  }
}
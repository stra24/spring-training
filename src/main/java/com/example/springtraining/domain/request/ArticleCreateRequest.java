package com.example.springtraining.domain.request;

import com.example.springtraining.domain.entity.Article;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 記事作成リクエスト。
 */
@Data
public class ArticleCreateRequest {

  private String title;
  private String content;
  private List<Long> tagIds = new ArrayList<>();

  // 新規登録用のArticleを作成して返す。
  public Article toNewArticle() {
    // 新規登録なので作成・更新日時は現在日時で揃える。
    LocalDateTime now = LocalDateTime.now();
    return new Article(null, this.title, this.content, now, now, null);
  }
}
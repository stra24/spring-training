package com.example.springtraining.domain.form;

import com.example.springtraining.domain.entity.Article;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleForm {

  private String title;
  private String content;
  private List<Long> tagIds;

  // 記事新規作成画面表示用に、空のArticleFormを作成する。
  public static ArticleForm empty() {
    return new ArticleForm(null, null, new ArrayList<>());
  }

  // 新規登録用のArticleを作成して返す。
  public Article toNewArticle() {
    // 新規登録なので作成・更新日時は現在日時で揃える。
    LocalDateTime now = LocalDateTime.now();
    return new Article(null, this.title, this.content, now, now, null);
  }

  // 更新用のArticleを作成して返す。
  public Article toUpdatedArticle(Article currentArticle) {
    // 更新なので createdAt はそのままにして、updatedAt だけ現在日時（更新時の日時）にする。
    return new Article(
        currentArticle.getId(),
        this.title,
        this.content,
        currentArticle.getCreatedAt(),
        LocalDateTime.now(),
        currentArticle.getVersion()
    );
  }
}
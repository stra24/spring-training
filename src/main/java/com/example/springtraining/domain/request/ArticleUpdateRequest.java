package com.example.springtraining.domain.request;

import com.example.springtraining.domain.entity.Article;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 記事更新リクエスト。
 */
@Data
public class ArticleUpdateRequest {

  private String title;
  private String content;
  private List<Long> tagIds = new ArrayList<>();

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
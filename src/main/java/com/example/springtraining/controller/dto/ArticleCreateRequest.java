package com.example.springtraining.controller.dto;

import com.example.springtraining.domain.article.ArticleForm;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 記事登録APIのリクエストボディ用DTO。
 * クライアントから受け取るJSONをこのクラスにマッピングする。
 */
@Data
public class ArticleCreateRequest {

  private String title;
  private String content;
  private List<Long> tagIds = new ArrayList<>();

  /**
   * 自身をArticleFormへ変換する。
   */
  public ArticleForm toForm() {
    ArticleForm form = new ArticleForm();
    form.setTitle(this.title);
    form.setContent(this.content);
    form.setTagIds(this.tagIds);
    return form;
  }
}
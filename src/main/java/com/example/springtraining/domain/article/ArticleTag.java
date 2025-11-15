package com.example.springtraining.domain.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("article_tag")
public class ArticleTag {

  private Long tagId;
}
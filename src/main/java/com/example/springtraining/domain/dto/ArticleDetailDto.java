package com.example.springtraining.domain.dto;

import com.example.springtraining.domain.entity.Article;
import com.example.springtraining.domain.entity.Comment;
import com.example.springtraining.domain.entity.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Value;

@Value
public class ArticleDetailDto {

  Long id;
  String title;
  String content;
  LocalDateTime updatedAt;
  List<CommentDto> comments;
  List<TagDto> tags;

  public static ArticleDetailDto from(Article article, List<Comment> comments, List<Tag> tags) {
    return new ArticleDetailDto(
        article.getId(),
        article.getTitle(),
        article.getContent(),
        article.getUpdatedAt(),
        comments.stream()
            .map(CommentDto::from)
            .toList(),
        tags.stream()
            .map(TagDto::from)
            .toList()
    );
  }
}

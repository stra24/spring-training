package com.example.springtraining.domain.dto;

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
}

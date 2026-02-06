package com.example.springtraining.domain.dto;

import com.example.springtraining.domain.entity.Comment;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class CommentDto {

  Long id;
  String content;
  LocalDateTime createdAt;

  public static CommentDto from(Comment comment) {
    return new CommentDto(
        comment.getId(),
        comment.getContent(),
        comment.getCreatedAt()
    );
  }
}
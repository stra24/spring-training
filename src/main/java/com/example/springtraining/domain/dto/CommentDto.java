package com.example.springtraining.domain.dto;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class CommentDto {

  Long id;
  String content;
  LocalDateTime createdAt;
}
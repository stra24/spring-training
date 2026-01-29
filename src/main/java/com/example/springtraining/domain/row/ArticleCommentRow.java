package com.example.springtraining.domain.row;

import java.time.LocalDateTime;

public record ArticleCommentRow(
    Long articleId,
    String title,
    String content,
    LocalDateTime updatedAt,
    Long commentId,
    String commentContent,
    LocalDateTime commentCreatedAt
) {

}

package com.example.springtraining.repository;

import com.example.springtraining.domain.article.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
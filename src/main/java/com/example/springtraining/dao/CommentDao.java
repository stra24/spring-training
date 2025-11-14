package com.example.springtraining.dao;

import com.example.springtraining.domain.article.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentDao extends CrudRepository<Comment, Long> {
}
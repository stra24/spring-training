package com.example.springtraining.repository;

import com.example.springtraining.dao.CommentDao;
import com.example.springtraining.domain.entity.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CommentRepository {

  private final CommentDao commentDao;

  public void save(Comment comment) {
    commentDao.save(comment);
  }
}
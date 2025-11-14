package com.example.springtraining.repository;

import com.example.springtraining.dao.CommentDao;
import com.example.springtraining.domain.article.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CommentRepository {

  private final CommentDao dao;

  // 保存する。
  public void save(Comment comment) {
    dao.save(comment);
  }
}
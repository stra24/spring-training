package com.example.springtraining.dao;

import com.example.springtraining.domain.entity.Comment;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CommentDao extends CrudRepository<Comment, Long> {

  // 記事IDに紐づくコメント一覧を、作成日時の昇順で取得する。
  List<Comment> findByArticleIdOrderByCreatedAtAsc(Long articleId);
}
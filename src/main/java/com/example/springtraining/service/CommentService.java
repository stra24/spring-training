package com.example.springtraining.service;

import com.example.springtraining.domain.article.Comment;
import com.example.springtraining.domain.article.CommentForm;
import com.example.springtraining.repository.ArticleRepository;
import com.example.springtraining.repository.CommentRepository;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final ArticleRepository articleRepository;

  // （親である記事の保存はせずに）コメントを直接保存する。
  @Transactional
  public void addCommentDirect(Long articleId, CommentForm form) {

    // 1. 親（記事）が本当に存在するか確認する（存在しなかったら例外が発生する）
    articleRepository.findById(articleId);

    // 2. 子（コメント）を作る
    Comment comment = Comment.newComment(articleId, form.getContent());

    // 3. 子（コメント）だけ保存する
    commentRepository.save(comment);
  }

  // コメントを保存後、例外を発生させる。
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addCommentAndThrowException(Long articleId, String content) {
    commentRepository.save(Comment.newComment(articleId, content));
    throw new RuntimeException("Propagation.REQUIRES_NEW のメソッドで例外が発生");
  }
}
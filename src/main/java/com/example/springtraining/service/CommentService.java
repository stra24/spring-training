package com.example.springtraining.service;

import com.example.springtraining.domain.entity.Comment;
import com.example.springtraining.domain.form.CommentForm;
import com.example.springtraining.repository.ArticleRepository;
import com.example.springtraining.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {

  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;

  // 指定の記事にコメントを追加する。
  @Transactional
  public void addComment(Long articleId, CommentForm form) {

    // 親（記事）が存在しなかったら例外を投げる。
    articleRepository.findById(articleId)
        .orElseThrow(
            () -> new IllegalArgumentException("記事が見つかりませんでした。id = " + articleId));

    // コメント作成して保存する。
    Comment comment = Comment.newComment(articleId, form.getContent());
    commentRepository.save(comment);
  }

  // 指定の記事にコメントを追加後、例外を発生させる。
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addCommentAndThrowException(Long articleId, CommentForm form) {

    // 親（記事）が存在しなかったら例外を投げる。
    articleRepository.findById(articleId)
        .orElseThrow(
            () -> new IllegalArgumentException("記事が見つかりませんでした。id = " + articleId));

    // コメント作成して保存する。
    Comment comment = Comment.newComment(articleId, form.getContent());
    commentRepository.save(comment);

    // 例外を投げる。
    throw new RuntimeException("Propagation.REQUIRES_NEW のメソッドで例外が発生");
  }
}
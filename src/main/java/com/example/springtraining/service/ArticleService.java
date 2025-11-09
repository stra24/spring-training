package com.example.springtraining.service;

import com.example.springtraining.domain.article.Article;
import com.example.springtraining.domain.article.ArticleForm;
import com.example.springtraining.domain.article.Comment;
import com.example.springtraining.domain.article.CommentForm;
import com.example.springtraining.repository.ArticleRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ArticleService {

  private final ArticleRepository repository;

  @Transactional(readOnly = true)
  public List<Article> listAll() {
    return repository.findAll();
  }

  // 更新日時の降順で一覧を取得する。
  @Transactional(readOnly = true)
  public List<Article> listOrderedByUpdatedAtDesc() {
    return repository.findAllOrderedByUpdatedAtDesc();
  }

  // IDで1件取得する。
  @Transactional(readOnly = true)
  public Article get(Long id) {
    return repository.findById(id);
  }

  // 新規作成する。
  @Transactional
  public void create(ArticleForm form) {
    Article article = form.toNewArticle();
    repository.save(article);
  }

  // 更新する。
  @Transactional
  public void update(Long id, ArticleForm form) {
    // 1. 既存の記事を取得
    Article article = repository.findById(id);

    // 2. フォームの内容を既存の記事に上書き
    Article articleForUpdate = form.toUpdatedArticle(article);

    // 3. 保存（idが入っているのでUPDATEになる）
    repository.save(articleForUpdate);
  }

  // 削除する。
  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  // タイトルで検索する。
  @Transactional(readOnly = true)
  public List<Article> searchByTitleKeyword(String keyword) {
    return repository.searchByTitleKeyword(keyword);
  }

  // 該当ページの全ての記事を取得する。
  @Transactional(readOnly = true)
  public Page<Article> getPage(int page, int size) {
    return repository.findPageOrderedByUpdatedAtDesc(page, size);
  }

  // 既存の記事にコメントを追加する。
  @Transactional
  public void addCommentByArticle(Long articleId, CommentForm form) {
    // 1. 親（記事）を取得
    Article article = repository.findById(articleId);

    // 2. コメント（子）を生成
    Comment comment = Comment.newComment(articleId, form.getContent());

    // 3. 親（記事）に子（コメント）を追加
    article.addComment(comment);

    // 4. 親ごと保存（→子の既存データが一括DELETEされてから、今回の子のデータが一括INSERTされる）
    repository.save(article);
  }
}
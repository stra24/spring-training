package com.example.springtraining.repository;

import com.example.springtraining.dao.ArticleDao;
import com.example.springtraining.domain.article.Article;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ArticleRepository {

  private final ArticleDao dao;

  public List<Article> findAll() {
    List<Article> list = new ArrayList<>();
    dao.findAll().forEach(list::add);
    return list;
  }

  // 更新日時の降順で並び替えた一覧を取得する。
  public List<Article> findAllOrderedByUpdatedAtDesc() {
    return dao.findAllOrderByUpdatedAtDesc();
  }

  // IDを条件に1件を取得する。（存在しなければ例外をスロー）
  public Article findById(Long id) {
    return dao.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした id=" + id));
  }

  // 新規作成
  public void save(Article article) {
    dao.save(article);
  }
}

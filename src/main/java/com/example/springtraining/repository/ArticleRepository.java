package com.example.springtraining.repository;

import com.example.springtraining.dao.ArticleDao;
import com.example.springtraining.domain.entity.Article;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ArticleRepository {

  private final ArticleDao articleDao;

  public List<Article> findAll() {
    List<Article> list = new ArrayList<>();
    articleDao.findAll().forEach(list::add);
    return list;
  }

  // IDの降順で並び替えた一覧を取得する。
  public List<Article> findAllOrderByIdDesc() {
    return articleDao.findAllOrderByIdDesc();
  }

  // IDを条件に1件を取得する。（存在しなければ例外をスロー）
  public Article findById(Long id) {
    return articleDao.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした id=" + id));
  }
}
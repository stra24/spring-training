package com.example.springtraining.repository;

import com.example.springtraining.dao.ArticleDao;
import com.example.springtraining.domain.entity.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  // IDを条件に1件を取得する。
  public Optional<Article> findById(Long id) {
    return articleDao.findById(id);
  }

  // 保存する。
  public void save(Article article) {
    articleDao.save(article);
  }

  // IDを指定して1件削除する。
  public void deleteById(Long id) {
    articleDao.deleteById(id);
  }

  // タイトルに指定のキーワードを含む記事をIDの降順で取得する。
  public List<Article> findByTitleContainingOrderByIdDesc(String keyword) {
    return articleDao.findByTitleContainingOrderByIdDesc(keyword);
  }
}
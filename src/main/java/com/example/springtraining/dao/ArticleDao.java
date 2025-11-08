package com.example.springtraining.dao;

import com.example.springtraining.domain.article.Article;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleDao extends CrudRepository<Article, Long> {

  // 更新日時の降順で並び替えて取得する。
  @Query("SELECT * FROM articles ORDER BY updated_at DESC")
  List<Article> findAllOrderByUpdatedAtDesc();

  // タイトルに指定のキーワードを含む記事を、更新日時の降順で取得する。
  List<Article> findByTitleContainingOrderByUpdatedAtDesc(String keyword);
}

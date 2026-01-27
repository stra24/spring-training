package com.example.springtraining.dao;

import com.example.springtraining.domain.entity.Article;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleDao extends CrudRepository<Article, Long> {

  @Query("""
      	SELECT * 
      	FROM articles 
      	ORDER BY id DESC
      """)
  List<Article> findAllOrderByIdDesc();

  // タイトルに指定のキーワードを含む記事を、IDの降順で取得する。
  List<Article> findByTitleContainingOrderByIdDesc(String keyword);
}
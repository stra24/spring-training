package com.example.springtraining.dao;

import com.example.springtraining.domain.entity.Article;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleDao extends CrudRepository<Article, Long> {

  @Query("""
      	SELECT * 
      	FROM articles 
      	ORDER BY id DESC
      """)
  List<Article> findAllOrderByIdDesc();

  // タイトルに指定のキーワードを含む記事を、IDの降順で取得する。
  List<Article> findByTitleContainingOrderByIdDesc(String keyword);

  // ページング用。キーワードの部分一致で絞り、IDの降順で並び替えたうえで、先頭からoffset件以降のsize件だけ取得する。
  @Query("""
      SELECT * 
      FROM articles
      WHERE title LIKE CONCAT('%', :keyword, '%')
      ORDER BY id DESC
      LIMIT :size 
      OFFSET :offset
      """)
  List<Article> findPageByConditionOrderByIdDesc(
      @Param("keyword") String keyword,
      @Param("size") int size,
      @Param("offset") int offset
  );

  // ページング用。キーワードの部分一致で絞ったうえで、総件数を取得する。
  @Query("""
      SELECT COUNT(*) 
      FROM articles
      WHERE title LIKE CONCAT('%', :keyword, '%')
      """)
  long countByCondition(@Param("keyword") String keyword);
}
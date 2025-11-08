package com.example.springtraining.dao;

import com.example.springtraining.domain.article.Article;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleDao extends CrudRepository<Article, Long> {

  // 更新日時の降順で並び替えて取得する。
  @Query("SELECT * FROM articles ORDER BY updated_at DESC")
  List<Article> findAllOrderByUpdatedAtDesc();

  // タイトルに指定のキーワードを含む記事を、更新日時の降順で取得する。
  List<Article> findByTitleContainingOrderByUpdatedAtDesc(String keyword);

  // ページング用。（更新日時の降順で並び替えたうえで、先頭から offset 件以降の size 件だけ取得する）
  @Query("""
      SELECT * FROM articles
      ORDER BY updated_at DESC
      LIMIT :size OFFSET :offset
      """)
  List<Article> findPageOrderByUpdatedAtDesc(
      @Param("size") int size,
      @Param("offset") int offset
  );

  // ページ数を計算するための総件数を取得する。
  @Query("SELECT COUNT(*) FROM articles")
  long countAll();
}

package com.example.springtraining.dao;

import com.example.springtraining.domain.entity.Article;
import com.example.springtraining.domain.row.ArticleCommentRow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
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

  // IDに紐づく記事1件＋その記事のコメント一覧を取得する。
  @Query("""
      SELECT
        a.id         AS article_id,
        a.title      AS title,
        a.content    AS content,
        a.updated_at AS updated_at,
        c.id         AS comment_id,
        c.content    AS comment_content,
        c.created_at AS comment_created_at
      FROM articles a
      LEFT JOIN comments c
        ON c.article_id = a.id
      WHERE a.id = :id
      ORDER BY c.created_at ASC
      """)
  List<ArticleCommentRow> findDetailRowsById(@Param("id") Long id);

  // 悲観ロックのパターンA：@Lock を使った悲観ロック（派生クエリメソッド）
  @Lock(LockMode.PESSIMISTIC_WRITE)
  Optional<Article> findOneById(Long id);

  // 悲観ロックのパターンB：@Query + FOR UPDATE を使った悲観ロック（生SQL）
  @Query("SELECT * FROM articles WHERE id = :id FOR UPDATE")
  Optional<Article> findByIdForUpdate(@Param("id") Long id);

  // 記事IDに紐づく中間テーブルを全削除する。
  @Modifying
  @Query("""
      DELETE FROM article_tag
      WHERE article_id = :articleId
      """)
  void deleteArticleTags(@Param("articleId") Long articleId);

  // 中間テーブルに1件追加する。
  @Modifying
  @Query("""
      INSERT INTO article_tag(article_id, tag_id)
      VALUES (:articleId, :tagId)
      """)
  void insertArticleTag(
      @Param("articleId") Long articleId,
      @Param("tagId") Long tagId
  );
}
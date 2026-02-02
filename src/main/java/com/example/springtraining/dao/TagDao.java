package com.example.springtraining.dao;

import com.example.springtraining.domain.entity.Tag;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TagDao extends CrudRepository<Tag, Long> {

  // 記事IDに紐づくタグ一覧を取得する（N:N）
  @Query("""
      SELECT
        t.id   AS id,
        t.name AS name
      FROM tags t
      INNER JOIN article_tag at
         ON at.tag_id = t.id
      WHERE at.article_id = :articleId
      ORDER BY t.id
      """)
  List<Tag> findTagsByArticleId(@Param("articleId") Long articleId);
}
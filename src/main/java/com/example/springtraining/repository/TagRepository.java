package com.example.springtraining.repository;

import com.example.springtraining.dao.TagDao;
import com.example.springtraining.domain.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TagRepository {

  private final TagDao tagDao;

  public List<Tag> findAll() {
    List<Tag> list = new ArrayList<>();
    tagDao.findAll().forEach(list::add);
    return list;
  }

  // 記事IDに紐づくタグを全て取得する。
  public List<Tag> findTagsByArticleId(Long articleId) {
    return tagDao.findTagsByArticleId(articleId);
  }
}
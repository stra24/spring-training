package com.example.springtraining.repository;

import com.example.springtraining.dao.TagDao;
import com.example.springtraining.domain.article.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TagRepository {

  private final TagDao tagDao;

  // 全件を取得する。
  public List<Tag> findAll() {
    List<Tag> result = new ArrayList<>();
    tagDao.findAll().forEach(result::add);
    return result;
  }

  // 保存する。
  public Tag save(Tag tag) {
    return tagDao.save(tag);
  }
}
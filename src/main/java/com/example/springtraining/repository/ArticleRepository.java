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
}
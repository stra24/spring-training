package com.example.springtraining.dao;

import com.example.springtraining.domain.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleDao extends CrudRepository<Article, Long> {

}
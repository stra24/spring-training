package com.example.springtraining.service;

import com.example.springtraining.domain.article.Article;
import com.example.springtraining.repository.ArticleRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ArticleService {

  private final ArticleRepository repository;

  @Transactional(readOnly = true)
  public List<Article> listAll() {
    return repository.findAll();
  }
}
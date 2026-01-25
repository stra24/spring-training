package com.example.springtraining.service;

import com.example.springtraining.domain.dto.ArticleDto;
import com.example.springtraining.repository.ArticleRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;

  @Transactional(readOnly = true)
  public List<ArticleDto> getArticles() {
    return articleRepository.findAll().stream()
        .map(ArticleDto::from)
        .toList();
  }
}
package com.example.springtraining.service;

import com.example.springtraining.domain.dto.ArticleDto;
import com.example.springtraining.domain.entity.Article;
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

  // IDの降順で一覧を取得する。
  @Transactional(readOnly = true)
  public List<ArticleDto> getArticlesOrderByIdDesc() {
    return articleRepository.findAllOrderByIdDesc().stream()
        .map(ArticleDto::from)
        .toList();
  }

  // IDで1件取得する。（存在しなければ例外をスロー）
  @Transactional(readOnly = true)
  public ArticleDto getArticle(Long id) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("記事が見つかりませんでした。id = " + id));
    return ArticleDto.from(article);
  }
}
package com.example.springtraining.service;

import com.example.springtraining.domain.article.Tag;
import com.example.springtraining.repository.TagRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

  // タグを全件取得する。
  @Transactional(readOnly = true)
  public List<Tag> listAll() {
    return tagRepository.findAll();
  }

  // タグを新規作成する。
  @Transactional
  public Tag create(String name) {
    return tagRepository.save(new Tag(name));
  }
}
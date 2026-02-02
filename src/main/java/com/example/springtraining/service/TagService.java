package com.example.springtraining.service;

import com.example.springtraining.domain.dto.TagDto;
import com.example.springtraining.domain.entity.Tag;
import com.example.springtraining.repository.TagRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

  @Transactional(readOnly = true)
  public List<TagDto> getTags() {
    return tagRepository.findAll().stream()
        .map(tag -> new TagDto(tag.getId(), tag.getName()))
        .toList();
  }

  // 記事IDに紐づくタグIDをすべて取得する。
  @Transactional(readOnly = true)
  public List<Long> getTagIdsByArticleId(Long articleId) {
    return tagRepository.findTagsByArticleId(articleId).stream()
        .map(Tag::getId)
        .toList();
  }
}
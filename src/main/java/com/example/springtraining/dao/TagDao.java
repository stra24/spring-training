package com.example.springtraining.dao;

import com.example.springtraining.domain.article.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagDao extends CrudRepository<Tag, Long> {
}
package com.example.springtraining.domain.article;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("tags")
public class Tag {

  @Id
  private Long id;
  private String name;

  public Tag(String name) {
    this.name = name;
  }
}
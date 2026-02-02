package com.example.springtraining.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@AllArgsConstructor
@Table("tags")
public class Tag {

  @Id
  private Long id;
  private String name;
}
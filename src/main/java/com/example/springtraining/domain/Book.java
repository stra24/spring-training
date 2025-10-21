package com.example.springtraining.domain;

import lombok.Data;

@Data
public class Book {

  /**
   * ID
   */
  private final Long id;

  /**
   * タイトル
   */
  private final String title;

  /**
   * 著者
   */
  private final String author;
}
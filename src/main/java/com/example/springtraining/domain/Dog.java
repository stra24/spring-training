package com.example.springtraining.domain;

import lombok.Data;

@Data
public class Dog {

  /**
   * 名前
   */
  private final String name;

  /**
   * 年齢
   */
  private final Integer age;
}
package com.example.springtraining.domain;

import lombok.Data;

@Data
public class Profile {

  /**
   * 氏名
   */
  private final FullName fullName;

  /**
   * 年齢
   */
  private final int age;
}
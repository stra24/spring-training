package com.example.springtraining.domain;

import lombok.Data;

@Data
public class FullName {

  /**
   * 名
   */
  private final String firstName;

  /**
   * 姓
   */
  private final String lastName;
}
package com.example.springtraining.domain;

import lombok.Data;

@Data
public class RegisterForm {

  /**
   * 名前
   */
  private String name;

  /**
   * 年齢
   */
  private Integer age;
}
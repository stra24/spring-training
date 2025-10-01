package com.example.springtraining.domain;

import java.util.List;
import lombok.Data;

@Data
public class Student {

  /**
   * 生徒ID
   */
  private final String id;

  /**
   * 名前
   */
  private final String name;

  /**
   * 学年
   */
  private final int grade;

  /**
   * 選択科目
   */
  private final List<String> subjects;
}

package com.example.springtraining.domain;

import lombok.Data;

@Data
public class Task {

  /**
   * タスクID
   */
  private final String id;

  /**
   * タスクタイトル
   */
  private final String title;

  /**
   * タスク優先度
   */
  private final TaskPriority priority;
}
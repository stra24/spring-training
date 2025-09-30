package com.example.springtraining.domain;

import lombok.Data;

@Data
public class User {

  /**
   * ユーザーID
   */
  private final String id;

  /**
   * プロフィール
   */
  private final Profile profile;
}

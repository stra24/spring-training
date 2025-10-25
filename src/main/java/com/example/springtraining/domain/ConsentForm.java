package com.example.springtraining.domain;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConsentForm {

  @NotBlank(message = "お名前は必須です")
  private String name;

  @AssertTrue(message = "利用規約への同意が必要です")
  private boolean agreeTerms;   // フィールド agreeTerms が true であることを検証する。（falseなら検証失敗）

  @AssertFalse(message = "現在の状態では送信できません（blocked は false 必須）")
  private boolean blocked;      // フィールド blocked が false であることを検証する。（trueなら検証失敗）
}
package com.example.springtraining.domain;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProfileForm {

  @Min(value = 0, message = "年齢は0以上で入力してください")
  private Integer age; // 任意入力とする

  @AssertTrue(message = "年齢を入力する場合は20以上で入力してください")
  public boolean isAdultIfPresent() { // メソッド isAdultIfPresent の戻り値が true であることを検証する。（falseなら検証失敗）
    return age == null || age >= 20;
  }
}
package com.example.springtraining.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderForm {
  @NotBlank(message = "お名前は必須です")
  private String name;

  @NotBlank(message = "メールは必須です")
  @Email(message = "メール形式で入力してください")
  private String email;

  @Min(value = 0, message = "年齢は0以上で入力してください")
  private Integer age;

  @Valid // ← ★ポイント　クラス型フィールドに @Valid をつけると、Addressクラス内も検証される！
  private Address address;
}
package com.example.springtraining.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {

  @NotBlank(message = "名前は必須です")
  private String name;

  @NotBlank(message = "メールは必須です")
  @Email(message = "メール形式で入力してください")
  private String email;

  @Min(value = 0, message = "年齢は0以上で入力してください")
  private Integer age;
}
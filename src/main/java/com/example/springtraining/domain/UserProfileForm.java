package com.example.springtraining.domain;

import com.example.springtraining.validation.Create;
import com.example.springtraining.validation.Update;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileForm {

  // 更新時に必須
  @NotBlank(message = "IDは更新時に必須です", groups = Update.class)
  private String id;

  // 新規時に必須
  @NotBlank(message = "名前は新規作成時に必須です", groups = Create.class)
  private String name;

  // 新規・更新どちらでも形式チェック
  @NotBlank(message = "メールは必須です", groups = {Create.class, Update.class})
  @Email(message = "メール形式で入力してください", groups = {Create.class, Update.class})
  private String email;

  // 更新時のみ必須
  @NotBlank(message = "電話番号は更新時に必須です", groups = Update.class)
  private String phone;
}

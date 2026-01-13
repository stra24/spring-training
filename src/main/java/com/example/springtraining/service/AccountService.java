package com.example.springtraining.service;

import com.example.springtraining.domain.User;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class AccountService {

  // 引数の検証が行われる。
  public boolean register(
      @NotBlank(message = "name は必須です") String name,
      @Email(message = "email の形式が不正です") String email,
      @Min(value = 18, message = "age は 18 以上を指定してください") int age
  ) {
    return true;
  }

  // 戻り値の検証が行われる。（nullではないことの検証）
  @NotNull(message = "ユーザーは必ず見つかる想定です")
  public User load(String id) {
    return StringUtils.isEmpty(id) ? null : new User(id, null);
  }
}
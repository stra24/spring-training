package com.example.springtraining.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

@Validated
@Service
public class AccountService {

  // 引数の検証が行われる。
  public String register(
      @NotBlank(message = "name は必須です") String name,
      @Email(message = "email の形式が不正です") String email,
      @Min(value = 18, message = "age は 18 以上を指定してください") int age
  ) {
    return "registered:" + name;
  }

  // 戻り値の検証が行われる。（nullではないことの検証）
  @NotNull(message = "ユーザーは必ず見つかる想定です")
  public UserDto load(@NotBlank(message = "id は必須です") String id) {
    // 検証を体感したい場合は、一時的に return null; にしてみてください（例外が発生します）
    // return null;
    return new UserDto(id, "Taro");
  }

  // ユーザーDTO
  public record UserDto(String id, String name) {}
}
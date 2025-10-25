package com.example.springtraining.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Address {
  @NotBlank(message = "都道府県は必須です")
  private String prefecture;

  @NotBlank(message = "市区町村は必須です")
  private String city;

  @NotBlank(message = "郵便番号は必須です")
  @Pattern(regexp = "^(\\d{3}-?\\d{4})$", message = "郵便番号は 123-4567 または 1234567 で入力してください")
  private String postalCode;
}
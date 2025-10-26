package com.example.springtraining.service;

import com.example.springtraining.domain.UserProfileForm;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

  // 新規作成
  public String create(UserProfileForm form) {
    return String.format(
        "created: name=%s, email=%s, phone=%s",
        form.getName(),
        form.getEmail(),
        form.getPhone()
    );
  }

  // 更新
  public String update(UserProfileForm form) {
    return String.format(
        "updated: id=%s, name=%s, email=%s, phone=%s",
        form.getId(),
        convertDefaultIfBlank(form.getName()),
        form.getEmail(),
        form.getPhone()
    );
  }

  private String convertDefaultIfBlank(String s) {
    return (s == null || s.isBlank()) ? "(未入力)" : s;
  }
}

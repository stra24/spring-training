package com.example.springtraining.controller.mvc;

import com.example.springtraining.domain.UserProfileForm;
import com.example.springtraining.service.UserProfileService;
import com.example.springtraining.validation.Create;
import com.example.springtraining.validation.Update;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validation/group")
public class GroupValidationController {

  private final UserProfileService service;

  public GroupValidationController(UserProfileService service) {
    this.service = service;
  }

  // フォーム画面表示用
  @GetMapping
  public String index(Model model) {
    model.addAttribute("title", "グループ検証デモ");
    model.addAttribute("subtitle",
        "同一画面で Create / Update の @Validated(グループ) を体験できます");
    return "validation/group/form";
  }

  // 新規作成（Create グループで検証）
  @PostMapping
  public String create(@Validated(Create.class) UserProfileForm form, Model model) {
    String message = service.create(form);
    model.addAttribute("title", "グループ検証：新規作成 完了");
    model.addAttribute("subtitle", "Create グループでの入力チェックを通過しました");
    model.addAttribute("lines", List.of("処理区分: CREATE", message));
    return "validation/group/form";
  }

  // 更新（Update グループで検証）
  @PostMapping("/{id}")
  public String update(@PathVariable String id,
      @Validated(Update.class) UserProfileForm form,
      Model model) {
    form.setId(id);
    String message = service.update(form);
    model.addAttribute("title", "グループ検証：更新 完了");
    model.addAttribute("subtitle", "Update グループでの入力チェックを通過しました");
    model.addAttribute("lines", List.of("処理区分: UPDATE", message));
    return "validation/group/form";
  }
}
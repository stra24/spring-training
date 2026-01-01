package com.example.springtraining.controller;

import com.example.springtraining.domain.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/model-attribute-method")
public class ModelAttributeMethodController {

    /**
     * ハンドラーメソッドの前に呼ばれるメソッド。
     *
     * ・戻り値あり
     * ・`@ModelAttribute("roles")`でModelに登録する際のキー名を指定している
     * ため、
     *
     * ・キー名: "roles"
     * ・値: このメソッドの戻り値
     * がModelに登録される。
     */
    @ModelAttribute("roles")
    public List<String> roles() {
        return List.of("一般ユーザー", "管理者", "ゲスト");
    }

    /**
     * ハンドラーメソッドの前に呼ばれるメソッド。
     *
     * ・戻り値あり
     * ・`@ModelAttribute`で名前を指定していない
     * のため、
     *
     * ・キー名: registerForm（戻り値の型であるRegisterFormのローワーキャメルケース）
     * ・値: このメソッドの戻り値
     * がModelに登録される。
     */
    @ModelAttribute
    public RegisterForm form() {
        return new RegisterForm();
    }

    /**
     * ハンドラーメソッドの前に呼ばれるメソッド。
     *
     * 戻り値なしのため、
     * model.addAttributeで指定したキーと値がModelに登録される。
     */
    @ModelAttribute
    public void common(Model model) {
        model.addAttribute("appName", "@ModelAttributeの動作確認");
        model.addAttribute("today", LocalDate.now());
    }

    /**
     * 一覧およびフォーム画面。
     */
    @GetMapping
    public String index() {
        return "model-attribute-method/form";
    }

    /**
     * フォームの送信結果画面。
     */
    @PostMapping
    public String submit(@ModelAttribute RegisterForm form) {
        return "model-attribute-method/result";
    }
}
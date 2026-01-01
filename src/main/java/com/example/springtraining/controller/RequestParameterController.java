package com.example.springtraining.controller;

import com.example.springtraining.domain.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestParameterController {

    /**
     * `@RequestParam`をつけると、
     * - リクエストパラメータが代入されること
     * を確認する用のフォーム画面。
     */
    @GetMapping("/request-param-form")
    public String requestParamForm() {
        return "request-parameter/request-param-form";
    }

    /**
     * `@RequestParam`をつけると、
     * - リクエストパラメータが代入されること
     * を確認する用の結果画面。
     */
    @GetMapping("/request-param-result")
    public String requestParamResult(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return "request-parameter/request-param-result";
    }

    /**
     * `@RequestParam`を省略しても、
     * - リクエストパラメータが代入されること
     * を確認する用の結果画面。
     */
    @GetMapping("/request-param-omit-result")
    public String requestParamOmitResult(String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return "request-parameter/request-param-result";
    }

    /**
     * `@ModelAttribute`をつけると、
     * - リクエストパラメータが代入されること
     * - Modelに自動的にオブジェクトが登録されること
     * を確認する用のフォーム画面。
     */
    @GetMapping("/model-attribute-form")
    public String modelAttributeForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "request-parameter/model-attribute-form";
    }

    /**
     * `@ModelAttribute`をつけると、
     * - リクエストパラメータが代入されること
     * - Modelに自動的にオブジェクトが登録されること
     * を確認する用の結果画面。
     */
    @PostMapping("/model-attribute-result")
    public String modelAttributeResult(@ModelAttribute RegisterForm form) {
        return "request-parameter/model-attribute-result";
    }

    /**
     * `@ModelAttribute`を省略しても、
     * - リクエストパラメータが代入されること
     * - Modelに自動的にオブジェクトが登録されること
     * を確認する用の結果画面。
     */
    @PostMapping("/model-attribute-omit-result")
    public String modelAttributeOmitResult(RegisterForm form) {
        return "request-parameter/model-attribute-result";
    }
}

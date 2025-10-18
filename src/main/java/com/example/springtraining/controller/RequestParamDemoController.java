package com.example.springtraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/rp-demo")
public class RequestParamDemoController {

  // トップ（フォーム集）
  @GetMapping
  public String index() {
    return "rp-demo";
  }

  // 1) required=true String
  @GetMapping("/req-true-string")
  public String reqTrueString(@RequestParam String x, Model model) {
    model.addAttribute("title", "(1) required=true × String");
    model.addAttribute("x", x);
    model.addAttribute("type", "String");
    return "rp-demo-result";
  }

  // 2) required=false String
  @GetMapping("/req-false-string")
  public String reqFalseString(@RequestParam(required = false) String x, Model model) {
    model.addAttribute("title", "(2) required=false × String");
    model.addAttribute("x", x); // 未指定なら null、空文字は "" のまま
    model.addAttribute("type", "String");
    return "rp-demo-result";
  }

  // 3) required=true int
  @GetMapping("/req-true-int")
  public String reqTrueInt(@RequestParam int x, Model model) {
    model.addAttribute("title", "(3) required=true × int");
    model.addAttribute("x", x); // 数値に変換できないと 400（型不一致）
    model.addAttribute("type", "int");
    return "rp-demo-result";
  }

  // 4) required=false int（危険：未指定は null→int 代入不可）
  @GetMapping("/req-false-int")
  public String reqFalseInt(@RequestParam(required = false) int x, Model model) {
    model.addAttribute("title", "(4) required=false × int（危険パターン）");
    model.addAttribute("x", x);
    model.addAttribute("type", "int");
    return "rp-demo-result";
  }

  // 5) required=true Integer
  @GetMapping("/req-true-integer")
  public String reqTrueInteger(@RequestParam(required = true) Integer x, Model model) {
    model.addAttribute("title", "(5) required=true × Integer");
    model.addAttribute("x", x); // 必須なので未指定は MissingServletRequestParameterException
    model.addAttribute("type", "Integer");
    return "rp-demo-result";
  }

  // 6) required=false Integer（安全：未指定/空文字→null を受け取れる）
  @GetMapping("/req-false-integer")
  public String reqFalseInteger(@RequestParam(required = false) Integer x, Model model) {
    model.addAttribute("title", "(6) required=false × Integer（安全）");
    model.addAttribute("x", x); // 未指定や空文字は null として渡る（既定のエディタ挙動）
    model.addAttribute("type", "Integer");
    return "rp-demo-result";
  }

  // 7) Optional<String>
  @GetMapping("/optional-string")
  public String optionalString(@RequestParam Optional<String> x, Model model) {
    model.addAttribute("title", "(7) Optional<String>");
    model.addAttribute("optionalPresent", x.isPresent());
    model.addAttribute("optionalValue", x.orElse("(empty)")); // 未指定→(empty) / 空文字→""
    model.addAttribute("type", "Optional<String>");
    return "rp-demo-result";
  }

  // 8) value と name は同義（同じ "q" を読む）
  // 例: /rp-demo/alias-equivalence?q=Hello
  @GetMapping("/alias-equivalence")
  public String aliasEquivalence(
      @RequestParam("q") String fromValueAttr,
      @RequestParam(name = "q") String fromNameAttr,
      Model model
  ) {
    model.addAttribute("title", "(8) value と name は同義の確認");
    model.addAttribute("valueAttrValue", fromValueAttr);
    model.addAttribute("nameAttrValue", fromNameAttr);
    return "rp-demo-result";
  }

  // 9) 省略時は引数名がパラメータ名になる
  // 例: /rp-demo/param-name-default?search=Java
  @GetMapping("/param-name-default")
  public String paramNameDefault(@RequestParam String search, Model model) {
    model.addAttribute("title", "(9) 属性省略時は引数名=パラメータ名の確認");
    model.addAttribute("searchValue", search);
    return "rp-demo-result";
  }

  // 10) defaultValue の網羅（String / Integer / Optional）
  // 例:
  //   /rp-demo/default-matrix                   （未指定）
  //   /rp-demo/default-matrix?user=&page=&kw=   （空文字）
  //   /rp-demo/default-matrix?user=%20&kw=%20   （半角スペース1文字）
  @GetMapping("/default-matrix")
  public String defaultMatrix(
      @RequestParam(defaultValue = "guest") String user,                 // 未指定/空文字 → "guest"
      @RequestParam(defaultValue = "1") Integer page,                    // 未指定/空文字 → 1
      @RequestParam(name = "kw", defaultValue = "") Optional<String> kw, // 未指定/空文字 → Optional.of("")
      Model model
  ) {
    model.addAttribute("title", "(10) defaultValue の挙動を型別に確認");
    model.addAttribute("userResolved", user);
    model.addAttribute("pageResolved", page);
    model.addAttribute("kwPresent", kw.isPresent());
    model.addAttribute("kwValue", kw.orElse("(empty)"));
    return "rp-demo-result";
  }

  // 11) required=true + defaultValue + int の場合の挙動
  // 例:
  //   /rp-demo/required-and-default            → 10
  //   /rp-demo/required-and-default?page=      → 10
  //   /rp-demo/required-and-default?page=5     → 5
  //   /rp-demo/required-and-default?page=abc   → 400（型不一致）
  @GetMapping("/required-and-default")
  public String requiredAndDefault(
      @RequestParam(name = "page", required = true, defaultValue = "10") int page,
      Model model
  ) {
    model.addAttribute("title", "(11) required=true + defaultValue + intの場合の挙動");
    model.addAttribute("pageResolved", page);
    return "rp-demo-result";
  }

  /*
    【注意：実行時検証は不可（起動時エラーになります）】
    value と name に「異なる」名前を同時指定すると、Spring の @AliasFor により
    設定不整合で起動時に例外が発生します。

    // 例：起動エラー（使わないでください）
    @GetMapping("/alias-conflict")
    public String aliasConflict(
            @RequestParam(value = "a", name = "b") String x, // ← NG: value と name が不一致のため、エラー
            Model model
    ) { ... }
  */
}

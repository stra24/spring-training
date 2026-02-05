package com.example.springtraining.controller.mvc;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/request-param-attribute")
public class RequestParamAttributeController {

  // トップ（フォーム集）
  @GetMapping
  public String index() {
    return "request-param-attribute/form";
  }

  // 1) required=true String
  @GetMapping("/req-true-string")
  public String reqTrueString(@RequestParam String x, Model model) {
    model.addAttribute("title", "(1) required=true × String");
    model.addAttribute("x", x);
    model.addAttribute("xText", render(x));
    model.addAttribute("type", "String");
    return "request-param-attribute/result";
  }

  // 2) required=false String
  @GetMapping("/req-false-string")
  public String reqFalseString(@RequestParam(required = false) String x, Model model) {
    model.addAttribute("title", "(2) required=false × String");
    model.addAttribute("x", x);
    model.addAttribute("xText", render(x)); // 未指定なら null、空文字は "" のまま
    model.addAttribute("type", "String");
    return "request-param-attribute/result";
  }

  // 3) required=true int
  @GetMapping("/req-true-int")
  public String reqTrueInt(@RequestParam int x, Model model) {
    model.addAttribute("title", "(3) required=true × int");
    model.addAttribute("x", x);
    model.addAttribute("xText", String.valueOf(x));
    model.addAttribute("type", "int");
    return "request-param-attribute/result";
  }

  // 4) required=false int（危険：未指定は null→int 代入不可）
  @GetMapping("/req-false-int")
  public String reqFalseInt(@RequestParam(required = false) int x, Model model) {
    model.addAttribute("title", "(4) required=false × int（危険パターン）");
    model.addAttribute("x", x);
    model.addAttribute("xText", String.valueOf(x));
    model.addAttribute("type", "int");
    return "request-param-attribute/result";
  }

  // 5) required=true Integer
  @GetMapping("/req-true-integer")
  public String reqTrueInteger(@RequestParam(required = true) Integer x, Model model) {
    model.addAttribute("title", "(5) required=true × Integer");
    model.addAttribute("x", x);
    model.addAttribute("xText", render(x));
    model.addAttribute("type", "Integer");
    return "request-param-attribute/result";
  }

  // 6) required=false Integer（安全：未指定/空文字→null を受け取れる）
  @GetMapping("/req-false-integer")
  public String reqFalseInteger(@RequestParam(required = false) Integer x, Model model) {
    model.addAttribute("title", "(6) required=false × Integer（安全）");
    model.addAttribute("x", x);
    model.addAttribute("xText", render(x));
    model.addAttribute("type", "Integer");
    return "request-param-attribute/result";
  }

  // 7) Optional<String>
  @GetMapping("/optional-string")
  public String optionalString(@RequestParam Optional<String> x, Model model) {
    model.addAttribute("title", "(7) Optional<String>");
    model.addAttribute("optionalPresent", x.isPresent());
    var value = x.orElse(null);
    model.addAttribute("optionalValueText", render(value)); // 未指定→null / 空文字→""
    model.addAttribute("type", "Optional<String>");
    return "request-param-attribute/result";
  }

  // 8) value と name は同義（= パラメータ名を明示指定している）
  // 例: /request-param-attribute/param-name-explicit?q=Hello
  @GetMapping("/param-name-explicit")
  public String paramNameExplicit(
      @RequestParam("q") String fromValueAttr,
      @RequestParam(name = "q") String fromNameAttr,
      Model model
  ) {
    model.addAttribute("title", "(8) パラメータ名を明示指定（value / name）");
    model.addAttribute("valueAttrValueText", render(fromValueAttr));
    model.addAttribute("nameAttrValueText", render(fromNameAttr));
    return "request-param-attribute/result";
  }

  // 9) 省略時は引数名がパラメータ名になる
  // 例: /request-param-attribute/param-name-default?search=Java
  @GetMapping("/param-name-default")
  public String paramNameDefault(@RequestParam String search, Model model) {
    model.addAttribute("title", "(9) 属性省略時は引数名=パラメータ名");
    model.addAttribute("searchValueText", render(search));
    return "request-param-attribute/result";
  }

  // 10) defaultValue の網羅（String / Integer / Optional）
  @GetMapping("/default-matrix")
  public String defaultMatrix(
      @RequestParam(defaultValue = "guest") String user,                 // 未指定/空文字 → "guest"
      @RequestParam(defaultValue = "1") Integer page,                    // 未指定/空文字 → 1
      @RequestParam(name = "kw", defaultValue = "") Optional<String> kw,
      // 未指定/空文字 → Optional.of("")
      Model model
  ) {
    model.addAttribute("title", "(10) defaultValue の挙動を型別に確認");
    model.addAttribute("userResolvedText", render(user));
    model.addAttribute("pageResolved", page);
    model.addAttribute("kwPresent", kw.isPresent());
    model.addAttribute("kwValueText", render(kw.orElse(null)));
    return "request-param-attribute/result";
  }

  // 11) required=true + defaultValue + int の場合の挙動
  @GetMapping("/required-and-default")
  public String requiredAndDefault(
      @RequestParam(name = "page", required = true, defaultValue = "10") int page,
      Model model
  ) {
    model.addAttribute("title", "(11) required=true + defaultValue + intの場合の挙動");
    model.addAttribute("pageResolvedReqDef", page);
    return "request-param-attribute/result";
  }

  // --- 表示用整形 ---
  private static String render(String s) {
    if (s == null) {
      return "null";
    }
    if (s.isEmpty()) {
      return "\"\"";
    }
    return s;
  }

  private static String render(Integer n) {
    return n == null ? "null" : n.toString();
  }
}

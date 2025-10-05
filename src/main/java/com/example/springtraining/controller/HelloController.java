package com.example.springtraining.controller;

import com.example.springtraining.config.AppConfig;
import com.example.springtraining.domain.Dog;
import com.example.springtraining.domain.FullName;
import com.example.springtraining.domain.Profile;
import com.example.springtraining.domain.RegisterForm;
import com.example.springtraining.domain.Student;
import com.example.springtraining.domain.Task;
import com.example.springtraining.domain.TaskPriority;
import com.example.springtraining.domain.User;
import com.example.springtraining.service.HelloService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

  private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
  private final HelloService helloService;

  @Autowired
  public HelloController(HelloService helloService) {
    this.helloService = helloService;
    log.info("HelloController の Bean を作成して箱に入れます");
  }

  @GetMapping("/hello")
  public String hello(Model model) {
    model.addAttribute("message", "Modelにセットした内容");

    String greetMessage = helloService.greet("太郎");
    model.addAttribute("greetMessage", greetMessage);
    return "hello";
  }

  @GetMapping("/user")
  public String showUser(Model model) {
    FullName fullName = new FullName("Hanako", "Yamada");
    Profile profile = new Profile(fullName, 25);
    User user = new User("1", profile);

    model.addAttribute("user", user);
    return "user";
  }

  @GetMapping("/list")
  public String list(Model model) {
    List<Student> studentList = List.of(
        new Student("STU_01", "山田 太郎", 3, List.of("国語", "数学", "英語")),
        new Student("STU_02", "佐藤 花子", 2, List.of("英語", "日本史")),
        new Student("STU_03", "伊藤 健", 1, List.of("数学"))
    );
    model.addAttribute("studentList", studentList);
    return "list";
  }

  @GetMapping("/map")
  public String map(Model model) {
    Map<String, Integer> fruitNameAndStockMap = new LinkedHashMap<>();
    fruitNameAndStockMap.put("りんご", 12);
    fruitNameAndStockMap.put("バナナ", 0);
    fruitNameAndStockMap.put("みかん", 30);
    fruitNameAndStockMap.put("ぶどう", 7);
    model.addAttribute("fruitNameAndStockMap", fruitNameAndStockMap);
    return "map";
  }

  @GetMapping("/set")
  public String set(Model model) {
    Set<String> clubSet = new LinkedHashSet<>(
        List.of("サッカー部", "吹奏楽部", "美術部", "科学部"));
    model.addAttribute("clubSet", clubSet);
    return "set";
  }

  @GetMapping("/enum")
  public String showEnum(Model model) {
    List<Task> taskList = List.of(
        new Task("TSK-01", "お風呂掃除", TaskPriority.LOW),
        new Task("TSK-02", "買い物", TaskPriority.HIGH),
        new Task("TSK-03", "夕飯作り", TaskPriority.MEDIUM)
    );
    model.addAttribute("taskList", taskList);
    model.addAttribute("priorityValues", TaskPriority.values());
    return "enum";
  }

  @GetMapping("/null-demo")
  public String nullDemo(Model model) {
    // ▼文字列まわり
    model.addAttribute("nickname", null);       // null
    model.addAttribute("nicknameEmpty", "");    // 空文字

    // ▼メソッド呼び出しに対する安全呼び出し ?. の確認用
    model.addAttribute("greeting", null); // null に対して length() を安全に呼びたい

    // ▼空のコレクションフレームワーク
    model.addAttribute("emptyList", List.of());  // 空リスト
    model.addAttribute("emptyMap", Map.of());    // 空マップ
    model.addAttribute("emptySet", Set.of());    // 空セット

    return "null-demo";
  }

  @GetMapping("/utility")
  public String utility(Model model) {
    // 日付・時刻
    model.addAttribute("ldt", LocalDateTime.of(2025, 8, 16, 22, 17, 33));
    model.addAttribute("ld",  LocalDate.of(2025, 8, 16));
    model.addAttribute("lt",  LocalTime.of(9, 5, 7, 123_000_000));

    // 数値
    model.addAttribute("num", 7);

    // 文字列
    model.addAttribute("upperSrc", "hello thymeleaf");
    model.addAttribute("lowerSrc", "SAYONARA SPRING");
    model.addAttribute("longText", "Thymeleaf utilities are handy for view-level formatting.");

    return "utility";
  }

  /**
   * フォーム画面を表示する。
   *
   * @param model Model
   * @return フォーム画面のテンプレート名
   */
  @GetMapping("/dog/form")
  public String showDogForm(Model model) {
    model.addAttribute(
        "dog",
        new Dog(null, null)
    );
    return "dog-form";
  }

  /**
   * フォームの送信結果画面を表示する。
   *
   * @param model Model
   * @return フォームの送信結果画面のテンプレート名
   */
  @PostMapping("/dog/form")
  public String submitDogForm(@ModelAttribute Dog dog, Model model) {
    model.addAttribute("submittedDog", dog);
    return "dog-result";
  }

  /**
   * `@RequestParam`をつけると、リクエストパラメータが代入されることを確認する用。
   */
  @GetMapping("/request-param")
  public String requestParam(@RequestParam String keyword, Model model) {
    model.addAttribute("keyword", keyword);
    return "request-param-result";
  }

  /**
   * `@ModelAttribute`をつけると、
   * - リクエストパラメータが代入されること
   * - Modelに自動的にオブジェクトが登録されること
   * を確認する用のフォーム画面。
   */
  @GetMapping("/model-attribute")
  public String modelAttributeFrom(Model model) {
    model.addAttribute("registerForm", new RegisterForm());
    return "model-attribute-form";
  }

  /**
   * `@ModelAttribute`をつけると、
   * - リクエストパラメータが代入されること
   * - Modelに自動的にオブジェクトが登録されること
   * を確認する用の結果画面。
   */
  @PostMapping("/model-attribute")
  public String modelAttributeResult(@ModelAttribute RegisterForm form) {
    return "model-attribute-result";
  }

  /**
   * `@RequestParam`を省略しても、リクエストパラメータが代入されることを確認する用。
   */
  @GetMapping("/request-param-omit")
  public String requestParamOmit(String keyword, Model model) {
    model.addAttribute("keyword", keyword);
    return "request-param-result";
  }

  /**
   * `@ModelAttribute`を省略しても、
   * - リクエストパラメータが代入されること
   * - Modelに自動的にオブジェクトが登録されること
   * を確認する用の結果画面。
   */
  @PostMapping("/model-attribute-omit")
  public String modelAttributeOmitResult(RegisterForm form) {
    return "model-attribute-result";
  }
}
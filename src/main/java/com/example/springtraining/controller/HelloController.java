package com.example.springtraining.controller;

import com.example.springtraining.config.AppConfig;
import com.example.springtraining.domain.FullName;
import com.example.springtraining.domain.Profile;
import com.example.springtraining.domain.Student;
import com.example.springtraining.domain.User;
import com.example.springtraining.service.HelloService;
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
}
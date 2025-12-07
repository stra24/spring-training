package com.example.springtraining.controller;

import com.example.springtraining.domain.FullName;
import com.example.springtraining.domain.Profile;
import com.example.springtraining.domain.User;
import com.example.springtraining.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  private static final Logger log = LoggerFactory.getLogger(HelloController.class);
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
}
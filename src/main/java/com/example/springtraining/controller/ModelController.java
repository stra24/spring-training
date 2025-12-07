package com.example.springtraining.controller;

import com.example.springtraining.domain.FullName;
import com.example.springtraining.domain.Profile;
import com.example.springtraining.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModelController {

  @GetMapping("/user")
  public String showUser(Model model) {
    FullName fullName = new FullName("Hanako", "Yamada");
    Profile profile = new Profile(fullName, 25);
    User user = new User("1", profile);
    model.addAttribute("user", user);
    return "model/user";
  }
}
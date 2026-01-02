package com.example.springtraining.controller;

import com.example.springtraining.domain.Book;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/path-variable")
public class PathVariableController {

  // 固定データ
  private static final List<Book> BOOKS = List.of(
      new Book(1L, "スッキリSpring", "Ito"),
      new Book(2L, "やさしいJava入門", "Sato"),
      new Book(3L, "ドメイン設計のキホン", "Tanaka")
  );

  // 一覧画面表示
  @GetMapping("/books")
  public String list(Model model) {
    model.addAttribute("books", BOOKS);
    return "path-variable/list";
  }

  // 詳細画面表示
  @GetMapping("/books/{bookId}")
  public String showBook(
      @PathVariable Long bookId,
      Model model
  ) {
    Book book = findById(bookId);
    model.addAttribute("book", book);
    return "path-variable/detail";
  }

  private Book findById(Long id) {
    return BOOKS.stream()
        .filter(b -> b.getId().equals(id))
        .findFirst()
        .orElseThrow();
  }
}
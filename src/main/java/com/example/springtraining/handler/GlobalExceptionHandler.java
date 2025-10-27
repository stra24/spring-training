package com.example.springtraining.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

  // NullPointerException を拾う
  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleNpe(NullPointerException ex, Model model, HttpServletRequest req) {
    model.addAttribute("handledBy", "@ControllerAdvice @ExceptionHandler");
    model.addAttribute("errorViewId", "templates/error/custom-error.html");
    model.addAttribute("exception", ex.getClass().getSimpleName());
    model.addAttribute("path", req.getRequestURI());
    return "error/custom-error";
  }
}

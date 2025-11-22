package com.example.springtraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 記事が見つからなかった場合にスローする例外。
 * この例外がスローされると、HTTPステータスコード 404 Not Found が返る。
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException {

  public ArticleNotFoundException(Long id) {
    super("記事が見つかりませんでした id=" + id);
  }
}

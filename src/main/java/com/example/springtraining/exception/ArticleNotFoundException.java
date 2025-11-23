package com.example.springtraining.exception;

/**
 * 記事が見つからなかった場合にスローする例外。
 */
public class ArticleNotFoundException extends RuntimeException {

  public ArticleNotFoundException(Long id) {
    super("記事が見つかりませんでした id=" + id);
  }
}

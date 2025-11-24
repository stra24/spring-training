package com.example.springtraining.handler;

import com.example.springtraining.controller.ArticleApiController;
import com.example.springtraining.controller.dto.ApiErrorResponse;
import com.example.springtraining.exception.ArticleNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * REST API向けの共通例外ハンドラ。
 *
 * ArticleApiController で発生した例外をここでキャッチし、
 * 自作のエラーレスポンス(JSON)に変換して返す。
 */
@RestControllerAdvice(assignableTypes = ArticleApiController.class)
public class RestExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

  /**
   * 記事が見つからない場合（404）。
   */
  @ExceptionHandler(ArticleNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleArticleNotFound(
      ArticleNotFoundException ex,
      HttpServletRequest request
  ) {
    ApiErrorResponse body = new ApiErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        "ARTICLE_NOT_FOUND",
        ex.getMessage(),
        request.getRequestURI(),
        Instant.now()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  /**
   * 「想定外エラー」（500）を一括で扱う。
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleException(
      Exception ex,
      HttpServletRequest request
  ) {
    // ログ出力
    log.error(ex.getMessage());

    ApiErrorResponse body = new ApiErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "INTERNAL_SERVER_ERROR",
        "予期しないエラーが発生しました。",
        request.getRequestURI(),
        Instant.now()
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
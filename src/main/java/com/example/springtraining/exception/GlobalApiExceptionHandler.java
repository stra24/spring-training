package com.example.springtraining.exception;

import com.example.springtraining.domain.dto.ApiErrorDto;
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
 */
@RestControllerAdvice(basePackages = "com.example.springtraining.controller.api")
public class GlobalApiExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalApiExceptionHandler.class);

  /**
   * リソースが見つからない場合（404）の例外処理を行う。
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiErrorDto> handleResourceNotFoundException(
      ResourceNotFoundException ex,
      HttpServletRequest request
  ) {
    ApiErrorDto body = new ApiErrorDto(
        HttpStatus.NOT_FOUND.value(),
        ex.getMessage(),
        request.getRequestURI(),
        Instant.now()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  /**
   * 「想定外エラー」（500）の例外処理を行う。
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorDto> handleException(
      Exception ex,
      HttpServletRequest request
  ) {
    // ログ出力
    log.error(ex.getMessage());

    ApiErrorDto body = new ApiErrorDto(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "予期しないエラーが発生しました。",
        request.getRequestURI(),
        Instant.now()
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
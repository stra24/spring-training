package com.example.springtraining.domain.dto;

import java.time.Instant;

/**
 * REST API 共通のエラーレスポンス。
 */
public record ApiErrorDto(
    int status,
    String message,
    String path,
    Instant timestamp
) {

}
package com.example.springtraining.controller.dto;

import java.time.Instant;

/**
 * REST API 共通のエラーレスポンス。
 */
public record ApiErrorResponse(
    int status,
    String errorCode,
    String message,
    String path,
    Instant timestamp
) {
}
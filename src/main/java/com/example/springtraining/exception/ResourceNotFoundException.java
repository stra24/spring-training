package com.example.springtraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * リソースが見つからなかった場合にスローする例外。この例外がスローされると、HTTP 404 Not Found を返す。
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(Class<?> resourceClass, String id) {
    super(resourceClass.getSimpleName() + " が見つかりませんでした。id = " + id);
  }
}
package com.mizegret.mps.mps_shared.exception;

import com.mizegret.mps.mps_shared.enums.ExtractFailureType;
import org.springframework.lang.NonNull;

public class ExtractFailureException extends RuntimeException {
  @NonNull private final String url;
  @NonNull private final String fieldName;
  @NonNull private final String selector;
  @NonNull private final ExtractFailureType kind;

  public ExtractFailureException(
      @NonNull String url,
      @NonNull String fieldName,
      @NonNull String selector,
      @NonNull ExtractFailureType kind) {
    super(String.format("Failed to extract field '%s' from URL '%s'", fieldName, url));
    this.url = url;
    this.fieldName = fieldName;
    this.selector = selector;
    this.kind = kind;
  }

  public ExtractFailureException(
      @NonNull String message,
      @NonNull String url,
      @NonNull String fieldName,
      @NonNull String selector,
      @NonNull ExtractFailureType kind) {
    super(message);
    this.url = url;
    this.fieldName = fieldName;
    this.selector = selector;
    this.kind = kind;
  }
}

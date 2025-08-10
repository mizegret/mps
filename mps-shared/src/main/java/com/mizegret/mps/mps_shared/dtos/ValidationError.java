package com.mizegret.mps.mps_shared.dtos;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
public class ValidationError {
  @NonNull private String field;
  @NonNull private String code;
  @NonNull private String message;
}

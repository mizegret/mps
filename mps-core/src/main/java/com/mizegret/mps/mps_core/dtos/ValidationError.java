package com.mizegret.mps.mps_core.dtos;

import lombok.Value;
import lombok.NonNull;

@Value
public class ValidationError {
  @NonNull private String field;
  @NonNull private String message;
}

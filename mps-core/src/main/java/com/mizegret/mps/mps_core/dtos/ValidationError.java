package com.mizegret.mps.mps_core.dtos;

import lombok.NonNull;
import lombok.Value;

@Value
public class ValidationError {
  @NonNull private String field;
  @NonNull private String message;
}

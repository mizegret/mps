package com.mizegret.mps.mps_shared.dtos;

import org.springframework.lang.NonNull;

import lombok.Value;

@Value
public class ValidationError{
    @NonNull
    private String field;
    @NonNull
    private String code;
    @NonNull
    private String message;
}

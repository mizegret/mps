package com.mizegret.mps.mps_shared.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

@Data
@RequiredArgsConstructor
public class ErrorResponse{
    @NonNull
    private final HttpStatus status;
    @NonNull
    private final List<String> messages;
    @NonNull
    private final OffsetDateTime timestamp;
}

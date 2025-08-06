package com.mizegret.mps.mps_shared.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ErrorResponseDto {

    private final int status;
    @NonNull
    private final List<String> messages;
    @NonNull
    private final OffsetDateTime timestamp;
}

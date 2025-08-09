package com.mizegret.mps.mps_shared.handler;

import com.mizegret.mps.mps_shared.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(Exception.class)
    @NonNull
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                Collections.singletonList("An unexpected error occurred. Please contact support."),
                OffsetDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @NonNull
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
        final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> "Field '%s': %s".formatted(error.getField(), error.getDefaultMessage()))
                        .collect(Collectors.toList()),
                OffsetDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

package com.mizegret.mps.mps_shared.handler;

import com.mizegret.mps.mps_shared.dtos.ValidationError;
import com.mizegret.mps.mps_shared.exception.BlockedException;
import com.mizegret.mps.mps_shared.exception.ExtractFailureException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @NonNull
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(@NonNull Exception e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please contact support.");
    }

    @NonNull
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(@NonNull MethodArgumentNotValidException ex){
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Validation failed for one or more fields. Please check the request body.");
        final List<ValidationError> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getCode(), error.getDefaultMessage()))
                .toList();
        problemDetail.setProperty("errors", validationErrors);

        return problemDetail;
    }

    @NonNull
    @ExceptionHandler({BlockedException.class, ExtractFailureException.class})
    public ProblemDetail handleBadGatewayException(@NonNull RuntimeException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
    }
}

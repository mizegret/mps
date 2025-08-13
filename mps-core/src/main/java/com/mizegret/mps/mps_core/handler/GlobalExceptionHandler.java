package com.mizegret.mps.mps_core.handler;

import com.mizegret.mps.mps_core.dtos.ValidationError;
import com.mizegret.mps.mps_core.exception.BlockedException;
import com.mizegret.mps.mps_core.exception.ExtractFailureException;
import com.mizegret.mps.mps_core.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path.Node;
import java.util.List;
import java.util.stream.Stream;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleException(@NonNull Exception e) {
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please contact support.");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleMethodArgumentNotValidException(
      @NonNull MethodArgumentNotValidException ex) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Validation failed for one or more fields. Please check the request body.");
    final List<ValidationError> fieldErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
            .toList();
    final List<ValidationError> globalErrors =
        ex.getBindingResult().getGlobalErrors().stream()
            .map(error -> new ValidationError("all", error.getDefaultMessage()))
            .toList();
    problemDetail.setProperty(
        "errors", Stream.concat(fieldErrors.stream(), globalErrors.stream()).toList());

    return problemDetail;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ProblemDetail handleConstraintViolationException(
      @NonNull ConstraintViolationException ex) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Validation failed for one or more fields. Please check the request's path and query parameters.");
    final List<ValidationError> validationErrors =
        ex.getConstraintViolations().stream()
            .map(
                violation ->
                    new ValidationError(getLastNodeName(violation), violation.getMessage()))
            .toList();

    problemDetail.setProperty("errors", validationErrors);
    return problemDetail;
  }

  private String getLastNodeName(@NonNull ConstraintViolation<?> v) {
    String lastNodeName = "";
    for (final Node p : v.getPropertyPath()) {
      lastNodeName = p.getName();
    }
    return lastNodeName;
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ProblemDetail handleTypeMismatch(@NonNull MethodArgumentTypeMismatchException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid parameter.");
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ProblemDetail handleNoResourceFoundException(@NonNull NoResourceFoundException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Not found endpoint...");
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ProblemDetail handleResourceNotFoundException(@NonNull ResourceNotFoundException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler({BlockedException.class, ExtractFailureException.class})
  public ProblemDetail handleBadGatewayException(@NonNull RuntimeException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
  }
}

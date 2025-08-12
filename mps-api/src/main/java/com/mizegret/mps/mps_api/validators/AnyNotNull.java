package com.mizegret.mps.mps_api.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AnyNotNullValidator.class)
public @interface AnyNotNull {
  String message() default "At least one field must be provided";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

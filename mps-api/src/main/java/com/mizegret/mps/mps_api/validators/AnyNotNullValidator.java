package com.mizegret.mps.mps_api.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class AnyNotNullValidator implements ConstraintValidator<AnyNotNull, Object> {

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (Objects.isNull(value)) return true;

    for (final Field f : value.getClass().getDeclaredFields()) {
      if (Modifier.isStatic(f.getModifiers())) continue;
      f.setAccessible(true);
      try {
        if (Objects.nonNull(f.get(value))) return true;
      } catch (IllegalAccessException ignored) {

      }
    }
    return false;
  }
}

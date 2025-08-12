package com.mizegret.mps.mps_core.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ResourceNotFoundException extends RuntimeException {
  String resourceName;
  String resourceId;

  public ResourceNotFoundException(String resourceName) {
    super("Resource not found: " + resourceName);
    this.resourceName = resourceName;
    this.resourceId = null;
  }

  public ResourceNotFoundException(String resourceName, String resourceId) {
    super("Resource not found: " + resourceName + " (ID: " + resourceId + ")");
    this.resourceName = resourceName;
    this.resourceId = resourceId;
  }
}

package com.crm.verification.core.exception;

public class ResourceExistsException extends RuntimeException {

  public ResourceExistsException() {
    super("Resource already exists");
  }

  public ResourceExistsException(String existingResource) {
    super(String.format("'%s' already exists", existingResource));
  }
}

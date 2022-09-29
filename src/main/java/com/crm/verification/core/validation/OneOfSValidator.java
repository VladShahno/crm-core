package com.crm.verification.core.validation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneOfSValidator implements ConstraintValidator<OneOfS, String> {

  private List<String> valueList;

  @Override
  public void initialize(OneOfS constraintAnnotation) {
    valueList = Arrays.stream(constraintAnnotation.values()).map(String::toUpperCase).collect(Collectors.toList());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return valueList.contains(value.toUpperCase());
  }
}

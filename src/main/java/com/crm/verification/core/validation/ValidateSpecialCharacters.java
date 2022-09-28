package com.crm.verification.core.validation;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.INVALID_SPECIAL_CHARACTERS_MESSAGE;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.SPECIAL_CHARACTER_VALIDATION_PATTERN;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "")
@Constraint(validatedBy = {})
public @interface ValidateSpecialCharacters {
  @OverridesAttribute(constraint = Pattern.class, name = "regexp") String regexp() default SPECIAL_CHARACTER_VALIDATION_PATTERN;

  @OverridesAttribute(constraint = Pattern.class, name = "message") String message() default INVALID_SPECIAL_CHARACTERS_MESSAGE;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

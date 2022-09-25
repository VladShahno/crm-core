package com.crm.verification.core.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  @UtilityClass
  public class Logging {
    public static final String COMPANY_NOT_FOUND = "Company with {} not found";
    public static final String LEAD_NOT_FOUND = "Lead with {} not found";
    public static final String DELETING_COMPANY = "Deleting company with {}";
    public static final String EMAIL = "email";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PACKAGE_NAME = "packageName";
    public static final String VERIFICATION_RESULT = "verificationResult";
    public static final String NOT_FOUND_ERROR_KEY_VALUE = "{} not found";
  }

  @UtilityClass
  public class Authors {
    public static final String VSHAKHNO = "vshakhno";
  }
}

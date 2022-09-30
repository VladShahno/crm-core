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
    public static final String PACKAGE = "Package";
    public static final String NAME = "name";
    public static final String PACKAGE_NAME = "packageName";
    public static final String VERIFICATION_RESULT = "verificationResult";
    public static final String NOT_FOUND_ERROR_KEY_VALUE = "{} not found";
    public static final String ADDRESS = "Address";
    public static final String COMPANY = "Company";
    public static final String LEAD = "Lead";
  }

  @UtilityClass
  public class Authors {
    public static final String VSHAKHNO = "vshakhno";
  }

  @UtilityClass
  public class CoreServiceValidation {

    public static final String INVALID_SPECIAL_CHARACTERS_MESSAGE = "Invalid %s. Special characters are not allowed";
    public static final String SPECIAL_CHARACTER_VALIDATION_PATTERN = "[^?^\"'@#*$%&()}{\\[\\]|\\\\/~+=<>]*$";
    public static final String FIRST_NAME_IS_REQUIRED = "{core.lead.name.first.required}";
    public static final String VERIFICATION_RESULT_REQUIRED = "{core.lead.verification.result.required}";
    public static final String LAST_NAME_REQUIRED = "{core.lead.name.last.required}";
    public static final String TITLE_REQUIRED = "{core.lead.title.required}";
    public static final String PROOF_LINK_REQUIRED = "{core.lead.proof-link.required}";
    public static final String COMMENTS_MAX_LENGTH = "{core.lead.company.comments.max.length}";
    public static final String COMPANY_NAME_REQUIRED = "{core.company.name.required}";
    public static final String INDUSTRY_REQUIRED = "{core.company.industry.required}";
    public static final String EMPLOYEES_REQUIRED = "{core.company.employees.required}";
    public static final String EMPLOYEES_PROOF_LINK_REQUIRED = "{core.company.proof-link.employees.required}";
    public static final String REVENUE_REQUIRED = "{core.company.revenue.required}";
    public static final String REVENUE_PROOF_LINK_REQUIRED = "{core.company.proof-link.revenue.required}";
    public static final String COUNTRY_REQUIRED = "{core.address.country.required}";
    public static final String STREET_REQUIRED = "{core.address.street.required}";
    public static final String CITY_REQUIRED = "{core.address.city.required}";
    public static final String STATE_REQUIRED = "{core.address.state.required}";
    public static final String POSTAL_CODE_REQUIRED = "{core.address.postal.code.required}";
    public static final String PHONE_NUMBER_REQUIRED = "{core.address.phone.number.required}";
    public static final String ADDRESS_ID_REQUIRED = "{core.address.id.required}";
    public static final String PACKAGE_NAME_REQUIRED = "{core.package.data.name.required}";
  }

  @UtilityClass
  public class VerificationResult {
    public static final String Y_ACCEPTED = "Y1:Accepted";
    public static final String Y2_ACCEPTED = "Y2:Accepted - by description";
    public static final String Y3_ACCEPTED = "Y3:Accepted - suspicious";
    public static final String Y4_ACCEPTED = "Y4:Accepted - questionable";
    public static final String N_A_COMPANY = "Reject: not appropriate company";
    public static final String N_A_TITLE = "Reject: not appropriate title";
    public static final String N_A_EMPLOYEES_SIZE = "Reject: not appropriate employees size";
    public static final String N_A_REVENUE = "Reject: not appropriate revenue";
    public static final String N_A_INDUSTRY = "Reject: not appropriate industry";
    public static final String N_A_ADDRESS = "Reject: not appropriate address";
  }
}

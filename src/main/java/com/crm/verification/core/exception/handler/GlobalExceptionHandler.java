package com.crm.verification.core.exception.handler;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;

import com.crm.verification.core.dto.response.exception.ExceptionResponse;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  Map<String, Object> details = new LinkedHashMap<>();

  public ResponseEntity<ExceptionResponse> handleException(
      String exceptionMessage, WebRequest webRequest, HttpStatus httpStatus) {
    ExceptionResponse exceptionResponse = ExceptionResponse.builder()
        .status(String.valueOf(httpStatus.value()))
        .error(httpStatus.getReasonPhrase())
        .message(exceptionMessage)
        .path(webRequest.getDescription(false).substring(4))
        //.timestamp(OffsetDateTime.now(ZoneId.systemDefault()))
        .details(details)
        .build();
    return new ResponseEntity<>(exceptionResponse, httpStatus);
  }

  @ExceptionHandler(value = {ResourceExistsException.class, ConstraintViolationException.class})
  public ResponseEntity<ExceptionResponse> handleClientExceptions(
      RuntimeException runtimeException, WebRequest request) {
    return handleException(runtimeException.getMessage(), request, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {ResourceNotFoundException.class})
  public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
      ResourceNotFoundException resourceNotFoundException, WebRequest request) {
    return handleException(resourceNotFoundException.getMessage(), request, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException validException, WebRequest request) {
    return handleException(validException.getBindingResult().getAllErrors().get(0).getDefaultMessage(), request,
        HttpStatus.BAD_REQUEST);
  }
}

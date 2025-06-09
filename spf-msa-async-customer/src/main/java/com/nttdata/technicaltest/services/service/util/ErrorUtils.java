package com.nttdata.technicaltest.services.service.util;

import com.google.common.collect.Iterators;
import com.nttdata.technicaltest.services.model.ErrorList;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;

@UtilityClass
@SuppressWarnings("java:S1118")
public final class ErrorUtils {
  private static final String BAD_REQUEST_ERROR_MESSAGE = "Bad Request";
  private static final String ERROR_FORMAT = "%s: must be valid";

  @NonNull
  public static String buildErrorCode(final int status) {
    return String.valueOf(status);
  }

  @NonNull
  private static String getConstraintViolationProperty(@NonNull final ConstraintViolation<?> constraintViolation) {
    return Iterators.getLast(constraintViolation.getPropertyPath().iterator()).toString();
  }

  @NonNull
  public static List<ErrorList> getErrors(@NonNull final ConstraintViolationException constraintViolationException) {
    return constraintViolationException.getConstraintViolations()
      .stream()
      .map(constraintViolation -> String.format(ERROR_FORMAT, getConstraintViolationProperty(constraintViolation)))
      .distinct()
      .map(businessMessage -> new ErrorList().message(BAD_REQUEST_ERROR_MESSAGE).businessMessage(businessMessage))
      .toList();
  }

  @NonNull
  public static List<ErrorList> getErrors(@NonNull final WebExchangeBindException webExchangeBindException) {
    return webExchangeBindException.getFieldErrors()
      .stream()
      .map(fieldError -> String.format(ERROR_FORMAT, fieldError.getField()))
      .distinct()
      .map(businessMessage -> new ErrorList().message(BAD_REQUEST_ERROR_MESSAGE).businessMessage(businessMessage))
      .toList();
  }
}

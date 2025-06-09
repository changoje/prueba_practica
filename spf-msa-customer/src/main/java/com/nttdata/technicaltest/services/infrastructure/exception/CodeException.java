package com.nttdata.technicaltest.services.infrastructure.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class CodeException extends Exception {
  @SuppressWarnings("java:S1068")
  private final int code;

  public CodeException(final int code, @NonNull final String message) {
    super(message);
    this.code = code;
  }

  public CodeException(@NonNull final Enum<?> code, @NonNull final String message) {
    this(code.ordinal(), message);
  }
}

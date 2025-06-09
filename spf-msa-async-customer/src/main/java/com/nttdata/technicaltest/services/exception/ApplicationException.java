package com.nttdata.technicaltest.services.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {
    @Getter
    private final String code;
    private final String message;

    @Getter
    private final HttpStatus status;

    public ApplicationException(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

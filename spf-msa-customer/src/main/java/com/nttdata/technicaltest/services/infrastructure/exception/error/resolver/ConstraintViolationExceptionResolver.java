package com.nttdata.technicaltest.services.infrastructure.exception.error.resolver;

import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ErrorUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.stream.Collectors;

public class ConstraintViolationExceptionResolver extends ErrorResolver {

    @Override
    protected int status() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    protected com.nttdata.technicaltest.services.model.Error buildError(
            final String requestPath,
            final Throwable throwable,
            final String version
    ) {
        final var exception = (ConstraintViolationException) throwable;

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        String detailMessage = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        return new com.nttdata.technicaltest.services.model.Error()
                .title("Bad input")
                .detail(detailMessage)
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}

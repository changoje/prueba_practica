package com.nttdata.technicaltest.services.infrastructure.exception.error.resolver;

import com.nttdata.technicaltest.services.model.ErrorList;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;

public class WebExchangeBindExceptionResolver extends ErrorResolver {
    private static final String BAD_REQUEST_ERROR_MESSAGE = "Bad Request";
    private static final String ERROR_FORMAT = "%s: must be valid";

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
        final var exception = (WebExchangeBindException) throwable;

        return new com.nttdata.technicaltest.services.model.Error()
                .title("Bad input")
                .detail(exception.getReason())
                .errorList(getErrors(exception))
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }

    public static List<ErrorList> getErrors(final WebExchangeBindException webExchangeBindException) {
        return webExchangeBindException.getFieldErrors()
                .stream()
                .map(fieldError -> String.format(ERROR_FORMAT, fieldError.getField()))
                .distinct()
                .map(businessMessage -> new ErrorList().message(BAD_REQUEST_ERROR_MESSAGE).businessMessage(businessMessage))
                .toList();
    }
}

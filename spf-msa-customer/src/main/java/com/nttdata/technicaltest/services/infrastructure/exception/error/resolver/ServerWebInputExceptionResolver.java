package com.nttdata.technicaltest.services.infrastructure.exception.error.resolver;

import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebInputException;

public class ServerWebInputExceptionResolver extends ErrorResolver {

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
        final var exception = (ServerWebInputException) throwable;

        String originalMessage = exception.getCause().getMessage();

        int dollarIndex = originalMessage.indexOf('$');

        String detailMessage;
        if (dollarIndex != -1) {
            detailMessage = originalMessage.substring(dollarIndex + 1);
        } else {
            detailMessage = originalMessage;
        }

        return new com.nttdata.technicaltest.services.model.Error()
                .title("Bad input")
                .detail(detailMessage)
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}

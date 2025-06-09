package com.nttdata.technicaltest.services.infrastructure.exception.error.resolver;

import com.nttdata.technicaltest.services.infrastructure.exception.CodeException;
import com.nttdata.technicaltest.services.model.Error;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ErrorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

@Slf4j
public class CodeExceptionResolver extends ErrorResolver<com.nttdata.technicaltest.services.model.Error> {
    @Override
    protected int status() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @NonNull
    @Override
    protected com.nttdata.technicaltest.services.model.Error buildError(
            @NonNull final String requestPath,
            @NonNull final Throwable throwable,
            @NonNull final String version
    ) {
        final var exception = (CodeException) throwable;
        return new Error()
                .title("Unexpected error")
                .detail(exception.getMessage())
                .instance(ErrorUtils.buildErrorCode(exception.getCode()))
                .type(requestPath);
    }
}

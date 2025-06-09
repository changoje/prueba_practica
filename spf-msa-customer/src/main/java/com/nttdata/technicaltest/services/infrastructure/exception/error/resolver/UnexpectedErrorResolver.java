package com.nttdata.technicaltest.services.infrastructure.exception.error.resolver;

import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ErrorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

@Slf4j
public class UnexpectedErrorResolver extends ErrorResolver<com.nttdata.technicaltest.services.model.Error> {
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
        return new com.nttdata.technicaltest.services.model.Error()
                .title("Unexpected error")
                .detail(throwable.getMessage())
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}

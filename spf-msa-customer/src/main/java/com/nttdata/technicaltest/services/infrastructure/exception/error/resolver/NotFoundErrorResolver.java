package com.nttdata.technicaltest.services.infrastructure.exception.error.resolver;

import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ErrorUtils;
import org.springframework.http.HttpStatus;

public class NotFoundErrorResolver extends ErrorResolver {
    @Override
    protected int status() {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    protected com.nttdata.technicaltest.services.model.Error buildError(final String requestPath,
                                                                        final Throwable throwable,
                                                                        final String version
    ) {
        return new com.nttdata.technicaltest.services.model.Error()
                .title("Not found")
                .detail(throwable.getMessage())
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}

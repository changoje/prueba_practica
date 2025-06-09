package com.nttdata.technicaltest.services.exception.error.resolver;


import com.nttdata.technicaltest.services.service.util.ErrorUtils;
import org.springframework.http.HttpStatus;

public class ConflictExceptionResolver extends ErrorResolver {

    @Override
    protected int status() {
        return HttpStatus.CONFLICT.value();
    }

    @Override
    protected com.nttdata.technicaltest.services.model.Error buildError(final String requestPath,
                               final Throwable throwable,
                               final String version
    ) {
        return new com.nttdata.technicaltest.services.model.Error()
                .title("Conflict")
                .detail(throwable.getMessage())
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }


}

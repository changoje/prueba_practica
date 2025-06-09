package com.nttdata.technicaltest.services.exception.error.resolver;


import com.nttdata.technicaltest.services.exception.CodeException;
import com.nttdata.technicaltest.services.service.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class NotFoundErrorResolver extends ErrorResolver<com.nttdata.technicaltest.services.model.Error> {
    @Override
    protected int status() {
        return HttpStatus.NOT_FOUND.value();
    }

    @NonNull
    @Override
    protected com.nttdata.technicaltest.services.model.Error buildError(
            @NonNull final String requestPath,
            @NonNull final Throwable throwable,
            @NonNull final String version
    ) {
        final var exception = (CodeException) throwable;
        return new com.nttdata.technicaltest.services.model.Error()
                .title("Not found")
                .detail(exception.getMessage())
                .instance(ErrorUtils.buildErrorCode(exception.getCode()))
                .type(requestPath);
    }
}

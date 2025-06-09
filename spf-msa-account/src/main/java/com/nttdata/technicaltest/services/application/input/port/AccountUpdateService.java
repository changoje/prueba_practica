package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import com.nttdata.technicaltest.services.model.PatchCustomerAccountRequest;
import reactor.core.publisher.Mono;

public interface AccountUpdateService {
    Mono<Void> patchCustomerAccount(ParametersBuilder parametersBuilder, PatchCustomerAccountRequest patchCustomerAccountRequest);
}

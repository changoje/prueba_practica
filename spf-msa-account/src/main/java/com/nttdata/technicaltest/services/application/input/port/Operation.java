package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.model.Transactions;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import reactor.core.publisher.Mono;

public interface Operation {
    Mono<Void> execute(AccountEntity accountEntity, double amount, Transactions typeOperation);
}

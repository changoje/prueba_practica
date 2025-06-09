package com.nttdata.technicaltest.services.application.output.port;

import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountTransactionEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface AccountTransactionRepositoryPort {
    Flux<AccountTransactionEntity> findByAccountIdAndTransactionDateBetween(Long accountId, LocalDateTime startDate, LocalDateTime endDate);

    Mono<AccountTransactionEntity> save(AccountTransactionEntity accountTransactionEntity);
}

package com.nttdata.technicaltest.services.application.output.port;

import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepositoryPort {
    Flux<AccountEntity> findByCustomerId(Long customerId);
    Mono<Void> deleteByCustomerIdAndAccountType(Long customerId, String accountType);
    Mono<AccountEntity> findByAccountNumber(String accountNumber);
    Mono<AccountEntity> save(AccountEntity accountEntity);
}

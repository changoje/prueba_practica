package com.nttdata.technicaltest.services.infrastructure.output.repository;

import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountEntityRepository extends ReactiveCrudRepository<AccountEntity, Long> {
    Flux<AccountEntity> findByCustomerId(Long customerId);
    Mono<Void> deleteByCustomerIdAndAccountType(Long customerId, String accountType);
    Mono<AccountEntity> findByAccountNumber(String accountNumber);
}

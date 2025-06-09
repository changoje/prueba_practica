package com.nttdata.technicaltest.services.infrastructure.output.repository;

import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountTransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface AccountTransactionEntityRepository extends ReactiveCrudRepository<AccountTransactionEntity, Long> {
    Flux<AccountTransactionEntity> findByAccountIdAndTransactionDateBetween(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
}

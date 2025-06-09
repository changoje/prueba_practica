package com.nttdata.technicaltest.services.infrastructure.output.adapter;

import com.nttdata.technicaltest.services.application.output.port.AccountTransactionRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.output.repository.AccountTransactionEntityRepository;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountTransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class AccountTransactionRepositoryAdapter implements AccountTransactionRepositoryPort {
    private final AccountTransactionEntityRepository accountTransactionEntityRepository;

    @Override
    public Flux<AccountTransactionEntity> findByAccountIdAndTransactionDateBetween(Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return accountTransactionEntityRepository.findByAccountIdAndTransactionDateBetween(accountId, startDate, endDate);
    }

    @Override
    public Mono<AccountTransactionEntity> save(AccountTransactionEntity accountTransactionEntity) {
        return accountTransactionEntityRepository.save(accountTransactionEntity);
    }
}

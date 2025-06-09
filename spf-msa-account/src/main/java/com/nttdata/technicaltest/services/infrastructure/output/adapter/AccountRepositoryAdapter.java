package com.nttdata.technicaltest.services.infrastructure.output.adapter;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.output.repository.AccountEntityRepository;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {
    private final AccountEntityRepository accountEntityRepository;

    @Override
    public Flux<AccountEntity> findByCustomerId(Long customerId) {
        return accountEntityRepository.findByCustomerId(customerId);
    }

    @Override
    public Mono<Void> deleteByCustomerIdAndAccountType(Long customerId, String accountType) {
        return accountEntityRepository.deleteByCustomerIdAndAccountType(customerId, accountType);
    }

    @Override
    public Mono<AccountEntity> findByAccountNumber(String accountNumber) {
        return accountEntityRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Mono<AccountEntity> save(AccountEntity accountEntity) {
        return accountEntityRepository.save(accountEntity);
    }
}

package com.nttdata.technicaltest.services.application.service.decorator;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.application.output.port.AccountTransactionRepositoryPort;
import com.nttdata.technicaltest.services.model.Transactions;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountTransactionEntity;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper.MapperTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public abstract class DecoratorTransactionService {
    protected final AccountTransactionRepositoryPort accountTransactionRepositoryPort;
    protected final AccountRepositoryPort accountRepositoryPort;

    protected Mono<Void> saveTransaction(AccountEntity accountEntity, double amount, Transactions typeOperation, BigDecimal updatedBalance) {
        return accountTransactionRepositoryPort
                .save(accountTransactionEntity(typeOperation, BigDecimal.valueOf(amount), accountEntity.getAccountId(), updatedBalance))
                .flatMap(savedTransaction -> updateAccountBalance(accountEntity, updatedBalance))
                .doOnSuccess(success -> log.info("Success to save account transaction!!"))
                .doOnError(error -> log.error("Error to save account transaction!! -> {}", error.getMessage()));
    }

    private Mono<Void> updateAccountBalance(AccountEntity accountEntity, BigDecimal newBalance) {
        accountEntity.setAvailableBalance(newBalance);
        return accountRepositoryPort
                .save(accountEntity)
                .then();
    }

    private AccountTransactionEntity accountTransactionEntity(Transactions transactionType,
                                                              BigDecimal amount,
                                                              Long accountId,
                                                              BigDecimal balance) {
        return MapperTransaction.INSTANCE.mapperToTransactionEntity(
                transactionType,
                amount,
                accountId,
                balance);
    }
}

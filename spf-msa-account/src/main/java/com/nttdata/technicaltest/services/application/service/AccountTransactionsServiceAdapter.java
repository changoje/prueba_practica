package com.nttdata.technicaltest.services.application.service;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.exception.AccountException;
import com.nttdata.technicaltest.services.infrastructure.exception.AccountNotAllowedException;
import com.nttdata.technicaltest.services.infrastructure.exception.AccountNotExistException;
import com.nttdata.technicaltest.services.infrastructure.exception.ClientNotFoundException;
import com.nttdata.technicaltest.services.model.PostCustomerTransactionsRequest;
import com.nttdata.technicaltest.services.application.input.port.AccountTransactionsService;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import com.nttdata.technicaltest.services.application.input.port.FactoryTransactionService;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountTransactionsServiceAdapter implements AccountTransactionsService {
    private final PersonCustomerService personCustomerService;
    private final AccountRepositoryPort accountRepositoryPort;
    private final FactoryTransactionService factoryTransactionService;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<Void> postCustomerTransactions(PostCustomerTransactionsRequest postCustomerTransactionsRequest) {
        return transactionalOperator.transactional(personCustomerService.getCustomer(postCustomerTransactionsRequest.getDocumentNumber())
                        .switchIfEmpty(Mono.error(new ClientNotFoundException()))
                        .flatMap(customerDto -> findAccount(postCustomerTransactionsRequest.getAccountNumber()))
                        .flatMap(accountEntity -> executeOperationIfAccountAllowed(accountEntity, postCustomerTransactionsRequest))
                ).doOnError(error -> log.error("Transaction failed, performing rollback: {}", error.getMessage()))
                .onErrorMap(throwable -> new AccountException(throwable.getMessage()));
    }


    private Mono<AccountEntity> findAccount(String accountNumber) {
        return accountRepositoryPort.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotExistException()))
                .doOnSuccess(success -> log.info("Success, find account!!"))
                .doOnError(error -> log.error("Error, find account!! -> {}", error.getMessage()));
    }

    private Mono<Void> executeOperationIfAccountAllowed(AccountEntity accountEntity, PostCustomerTransactionsRequest request) {
        if (!Constants.ACCOUNT_STATUS.equalsIgnoreCase(accountEntity.getStatus())) {
            return Mono.error(new AccountNotAllowedException());
        }
        return executeOperation(accountEntity, request);
    }

    private Mono<Void> executeOperation(AccountEntity accountEntity, PostCustomerTransactionsRequest request) {
        return factoryTransactionService.getOperation(request.getTransactions())
                .execute(accountEntity, request.getAmount(), request.getTransactions())
                .doOnError(error -> Mono.error(new AccountException(error.getMessage())));
    }
}
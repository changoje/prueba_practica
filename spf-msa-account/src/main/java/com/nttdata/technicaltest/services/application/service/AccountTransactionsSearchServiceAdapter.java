package com.nttdata.technicaltest.services.application.service;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.application.output.port.AccountTransactionRepositoryPort;
import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import com.nttdata.technicaltest.services.infrastructure.exception.DatabaseSavingOperationException;
import com.nttdata.technicaltest.services.model.Accounts;
import com.nttdata.technicaltest.services.model.CustomerTransactions;
import com.nttdata.technicaltest.services.application.input.port.AccountTransactionsSearchService;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.domain.dto.AccountTransactionsDto;
import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper.MapperTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountTransactionsSearchServiceAdapter implements AccountTransactionsSearchService {

    private final AccountRepositoryPort accountRepositoryPort;
    private final AccountTransactionRepositoryPort accountTransactionRepositoryPort;
    private final PersonCustomerService personCustomerService;

    @Override
    public Flux<CustomerTransactions> getCustomerTransaction(ParametersBuilder parametersBuilder) {
        return personCustomerService.getCustomer(parametersBuilder.getDocumentNumber())
                .flatMapMany(customerDto -> getAccountEntity(customerDto, parametersBuilder.getAccountType())
                        .collectList()
                        .flatMap(accountEntity -> getTransactions(accountEntity, parametersBuilder.getStartDate(), parametersBuilder.getEndDate())
                                .collectList()
                                .map(accounts -> MapperTransaction.INSTANCE.mapperToCustomerTransactions(customerDto, accounts))
                        )
                );
    }

    private Flux<AccountEntity> getAccountEntity(CustomerDto customerDto, String accountType) {
        return accountRepositoryPort.findByCustomerId(customerDto.getCustomerId())
                .switchIfEmpty(Mono.error(new DatabaseSavingOperationException()))
                .filter(accountEntity -> accountType != null ? accountEntity.getAccountType().equalsIgnoreCase(accountType) : Boolean.TRUE)
                .doOnError(error -> log.error("Error to find By CustomerId And AccountType"));
    }

    private Flux<Accounts> getTransactions(List<AccountEntity> accountEntity, String startDate, String endDate) {
        LocalDateTime dateIni = LocalDateTime.parse(startDate);
        LocalDateTime dateFin = LocalDateTime.parse(endDate);
        return Flux.fromIterable(accountEntity)
                .flatMap(accountEntity1 -> getTransactionsDto(accountEntity1, dateIni, dateFin)
                        .switchIfEmpty(Mono.empty())
                        .doOnComplete(() -> log.info("Success to get account transactions."))
                        .doOnError(error -> log.error("Error to get account transactions. -> {}", error.getMessage()))
                        .collectList()
                        .map(accountTransactionEntities -> MapperTransaction.INSTANCE.mapperToAccount(accountEntity1, accountTransactionEntities))
                );
    }

    private Flux<AccountTransactionsDto> getTransactionsDto(AccountEntity accountEntity, LocalDateTime dateIni, LocalDateTime dateFin) {
        return accountTransactionRepositoryPort.findByAccountIdAndTransactionDateBetween(accountEntity.getAccountId(), dateIni, dateFin)
                .map(accountTransactionEntity -> MapperTransaction.INSTANCE.mapperToAccountTransactionsDto(accountTransactionEntity, accountEntity.getInitialBalance()))
                .scan((previous, current) -> {
                    current.setBalanceOld(previous.getBalance());
                    return current;
                });
    }
}

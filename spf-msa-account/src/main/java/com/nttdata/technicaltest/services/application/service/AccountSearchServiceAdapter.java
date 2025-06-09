package com.nttdata.technicaltest.services.application.service;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import com.nttdata.technicaltest.services.model.GetCustomerAccountResponse;
import com.nttdata.technicaltest.services.application.input.port.AccountSearchService;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper.MapperAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountSearchServiceAdapter implements AccountSearchService {
    private final AccountRepositoryPort accountRepositoryPort;
    private final PersonCustomerService personCustomerService;

    @Override
    public Mono<GetCustomerAccountResponse> getCustomerAccount(ParametersBuilder parametersBuilder) {
        return personCustomerService.getCustomer(parametersBuilder.getDocumentNumber())
                .flatMap(customerDto -> getAccounts(customerDto.getCustomerId())
                        .collectList()
                        .map(accountEntities -> MapperAccount.INSTANCE.mapperToGetCustomerAccountResponse(customerDto, accountEntities)));
    }


    private Flux<AccountEntity> getAccounts(Long customerId) {
        return accountRepositoryPort.findByCustomerId(customerId)
                .doOnError(error -> log.error("Error, to get person By Document Number. -> {}", error.getMessage()))
                .switchIfEmpty(Mono.empty());
    }
}
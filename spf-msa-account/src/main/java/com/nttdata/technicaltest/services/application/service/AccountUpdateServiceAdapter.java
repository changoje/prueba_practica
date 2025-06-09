package com.nttdata.technicaltest.services.application.service;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import com.nttdata.technicaltest.services.infrastructure.exception.DatabaseSavingOperationException;
import com.nttdata.technicaltest.services.model.PatchCustomerAccountRequest;
import com.nttdata.technicaltest.services.application.input.port.AccountUpdateService;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountUpdateServiceAdapter implements AccountUpdateService {
    private final AccountRepositoryPort accountRepositoryPort;
    private final PersonCustomerService personCustomerService;

    @Override
    public Mono<Void> patchCustomerAccount(ParametersBuilder parametersBuilder, PatchCustomerAccountRequest patchCustomerAccountRequest) {
        return personCustomerService.getCustomer(parametersBuilder.getDocumentNumber())
                .flatMap(customerDto -> accountRepositoryPort.findByCustomerId(customerDto.getCustomerId())
                        .switchIfEmpty(Mono.error(new DatabaseSavingOperationException()))
                        .filter(accountEntities -> accountEntities.getAccountType().equalsIgnoreCase(parametersBuilder.getAccountType()))
                        .flatMap(accountEntity -> {
                            accountEntity.setStatus(patchCustomerAccountRequest.getStatus().getCode());
                            return accountRepositoryPort.save(accountEntity);
                        }).then()
                );
    }

}

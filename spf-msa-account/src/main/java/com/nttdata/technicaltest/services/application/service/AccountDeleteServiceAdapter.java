package com.nttdata.technicaltest.services.application.service;

import com.nttdata.technicaltest.services.application.input.port.AccountDeleteService;
import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountDeleteServiceAdapter implements AccountDeleteService {
    private final AccountRepositoryPort accountRepositoryPort;
    private final PersonCustomerService personCustomerService;
    @Override
    public Mono<Void> deleteCustomerAccount(ParametersBuilder parametersBuilder) {
        return personCustomerService.getCustomer(parametersBuilder.getDocumentNumber())
                .flatMapMany(customerDto -> accountRepositoryPort.deleteByCustomerIdAndAccountType(customerDto.getCustomerId(), parametersBuilder.getAccountType())
                        .doOnSuccess(success -> log.info("Success to delete customer account!"))
                        .doOnError(error -> log.error("Error to delete customer account!")))
                .then();
    }
}

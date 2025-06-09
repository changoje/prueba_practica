package com.nttdata.technicaltest.services.application.service;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.application.output.port.CustomerRedisRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.exception.DatabaseSavingOperationException;
import com.nttdata.technicaltest.services.model.PostCustomerAccountRequest;
import com.nttdata.technicaltest.services.application.input.port.AccountCreationService;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper.MapperAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountCreationServiceAdapter implements AccountCreationService {
    private final AccountRepositoryPort accountEntityRepository;
    private final CustomerRedisRepositoryPort customerRedisRepository;
    private final PersonCustomerService personCustomerService;

    private static final Random RANDOM = new Random();

    @Override
    public Mono<Void> postCustomerAccount(PostCustomerAccountRequest postCustomerAccountRequest) {
        return processGetCustomer(postCustomerAccountRequest.getDocumentNumber())
                .flatMap(customerDto -> saveAccount(postCustomerAccountRequest, customerDto))
                .then();
    }

    private Mono<CustomerDto> processGetCustomer(String documentNumber) {
        return findCustomerCache(documentNumber)
                .switchIfEmpty(personCustomerService.getCustomer(documentNumber));
    }

    private Mono<Void> saveAccount(PostCustomerAccountRequest postCustomerAccountRequest, CustomerDto customerDto) {
        return Mono.just(postCustomerAccountRequest)
                .map(req -> createAccountData(req, customerDto))
                .flatMap(accountEntityRepository::save)
                .doOnSuccess(success -> log.info("Success to save account data!!"))
                .doOnError(error -> log.error("Error to save account data!! -> {}", error.getMessage()))
                .onErrorMap(error -> new DatabaseSavingOperationException())
                .then(customerRedisRepository.deleteCustomer());
    }

    private AccountEntity createAccountData(PostCustomerAccountRequest request, CustomerDto customerDto) {
        return MapperAccount.INSTANCE.mapperToAccount(
                generateRandomAccountNumber(),
                BigDecimal.valueOf(request.getInitialBalance()),
                request.getAccountType().getValue(),
                customerDto.getCustomerId());
    }

    private Mono<CustomerDto> findCustomerCache(String documentNumber) {
        return customerRedisRepository.find(documentNumber)
                .switchIfEmpty(Mono.empty())
                .doOnSuccess(success -> log.info("Success to find customer!"))
                .doOnError(error -> log.error("Error to find customer! -> {}", error.getMessage()));
    }

    private static String generateRandomAccountNumber() {
        return RANDOM.ints(10, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }
}

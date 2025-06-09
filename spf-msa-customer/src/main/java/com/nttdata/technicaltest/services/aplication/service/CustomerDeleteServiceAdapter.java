package com.nttdata.technicaltest.services.aplication.service;

import com.nttdata.technicaltest.services.infrastructure.exception.DatabaseSavingOperationException;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.aplication.input.port.CustomerDeleteService;
import com.nttdata.technicaltest.services.infrastructure.output.repository.PersonEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerDeleteServiceAdapter implements CustomerDeleteService {
    private final PersonEntityRepository personEntityRepository;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<Void> deleteCustomer(String documentNumber) {
        return deleteCustomerByDocumentNumber(documentNumber);
    }

    private Mono<Void> deleteCustomerByDocumentNumber(String documentNumber) {
        return transactionalOperator.transactional(personEntityRepository.findByDocumentNumber(documentNumber)
                .switchIfEmpty(Mono.error(new DatabaseSavingOperationException()))
                .flatMap(personDto -> deleteCustomerAndPerson(personDto.getPersonId()))
                .doOnSuccess(success -> log.info("Successfully deleted customer with document number: {}", documentNumber))
                .doOnError(error -> log.error("Error deleting customer: {}", error.getMessage()))
        ).doOnError(error -> log.error("Transaction failed, performing rollback: {}", error.getMessage()));
    }

    private Mono<Void> deleteCustomerAndPerson(Long personId) {
        return customerRepositoryPort.deleteByPersonId(personId)
                .doOnSuccess(success -> log.info("Successfully deleted customer by Person ID: {}", personId))
                .doOnError(error -> log.error("Error to delete customer by person id -> {}", error.getMessage()))
                .onErrorResume(this::handleDeleteError)
                .then(personEntityRepository.deleteById(personId)
                        .doOnSuccess(success -> log.info("Successfully deleted person by ID: {}", personId))
                        .doOnError(error -> log.error("Error to delete person -> {}", error.getMessage()))
                        .onErrorResume(this::handleDeleteError)
                );
    }

    private Mono<Void> handleDeleteError(Throwable error) {
        return Mono.error(new DatabaseSavingOperationException());
    }
}

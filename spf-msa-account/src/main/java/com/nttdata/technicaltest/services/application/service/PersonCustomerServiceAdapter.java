package com.nttdata.technicaltest.services.application.service;

import com.nttdata.technicaltest.services.application.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.application.output.port.PersonRepositoryPort;
import com.nttdata.technicaltest.services.application.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonCustomerServiceAdapter implements PersonCustomerService {
    private final CustomerRepositoryPort customerRepositoryPort;
    private final PersonRepositoryPort personRepositoryPort;

    @Override
    public Mono<CustomerDto> getCustomer(String documentNumber) {
        return findByDocumentNumber(documentNumber)
                .flatMap(personEntity -> findByPersonId(personEntity.getPersonId())
                        .map(customerDto -> {
                            customerDto.setName(personEntity.getName());
                            customerDto.setDocumentNumber(personEntity.getDocumentNumber());
                            return customerDto;
                        }));
    }

    private Mono<PersonEntity> findByDocumentNumber(String documentNumber) {
        return personRepositoryPort.findByDocumentNumber(documentNumber)
                .doOnSuccess(personEntity -> log.info("Success find person By Document Number"))
                .doOnError(error -> log.error("Error find person By Document Number -> {}", error.getMessage()));
    }

    private Mono<CustomerDto> findByPersonId(Long personId) {
        return customerRepositoryPort.findByPersonId(personId)
                .doOnSuccess(success -> log.info("Success, find customer By Person Id."))
                .doOnError(error -> log.error("Error, find customer By Person Id -> {}", error.getMessage()));
    }
}

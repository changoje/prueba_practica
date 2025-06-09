package com.nttdata.technicaltest.services.aplication.service;

import com.nttdata.technicaltest.services.aplication.output.port.PersonRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.exception.CustomerPersistException;
import com.nttdata.technicaltest.services.infrastructure.exception.PersonPersistException;
import com.nttdata.technicaltest.services.infrastructure.exception.UserInvalidException;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.aplication.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.domain.dto.CustomerEntityDto;
import com.nttdata.technicaltest.services.domain.dto.PersonEntityDto;
import com.nttdata.technicaltest.services.domain.dto.PutCustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.CustomerEntity;
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
    public Mono<PersonEntityDto> findPersonByDocumentNumber(String documentNumber) {
        return personRepositoryPort.findByDocumentNumber(documentNumber)
                .doOnSuccess(personEntity -> log.info("Success find person By Document Number"))
                .doOnError(error -> log.error("Error find person By Document Number -> {}", error.getMessage()));
    }

    @Override
    public Mono<PersonEntity> savePerson(PersonEntity personEntity) {
        return personRepositoryPort.save(personEntity)
                .doOnSuccess(person -> log.info("Success, saving the person's data."))
                .doOnError(error -> log.error("Error, saving the person's data. -> {}", error.getMessage()))
                .onErrorMap(throwable -> new PersonPersistException());
    }

    @Override
    public Mono<Void> saveCustomer(CustomerEntity customerEntity) {
        return customerRepositoryPort.saveCustomer(customerEntity)
                .doOnSuccess(success -> log.info("Successfully saved customer data."))
                .doOnError(error -> log.error("Error, saved customer data.  -> {}", error.getMessage())).then()
                .onErrorMap(throwable -> new CustomerPersistException());
    }

    @Override
    public Mono<CustomerEntityDto> findCustomerByPersonId(Long personId) {
        return customerRepositoryPort.findByPersonId(personId)
                .doOnSuccess(success -> log.info("Success, find customer By Person Id."))
                .doOnError(error -> log.error("Error, find customer By Person Id -> {}", error.getMessage()));
    }

    @Override
    public Mono<CustomerEntityDto> validateUser(String userName) {
        return customerRepositoryPort.validUser(userName)
                .doOnSuccess(person -> log.info("Success, validating user."))
                .doOnError(error -> log.error("Error, validating user. -> {}", error.getMessage()))
                .switchIfEmpty(Mono.error(new UserInvalidException()));
    }

    @Override
    public Mono<Void> updateCustomer(PersonEntity personEntity, PutCustomerDto putCustomerDto) {
        return customerRepositoryPort.updateCustomer(putCustomerDto.getStatus().getCode(), personEntity.getPersonId())
                .doOnSuccess(success -> log.info("Success in updating customer data: {}", success))
                .doOnError(error -> log.error("Failed to update customer data: {}", error.getMessage()));
    }
}

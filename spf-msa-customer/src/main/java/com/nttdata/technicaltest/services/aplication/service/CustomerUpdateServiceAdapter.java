package com.nttdata.technicaltest.services.aplication.service;

import com.nttdata.technicaltest.services.domain.dto.PersonEntityDto;
import com.nttdata.technicaltest.services.infrastructure.exception.DatabaseSavingOperationException;
import com.nttdata.technicaltest.services.aplication.input.port.CustomerUpdateService;
import com.nttdata.technicaltest.services.aplication.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.domain.dto.CustomerEntityDto;
import com.nttdata.technicaltest.services.domain.dto.PutCustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerUpdateServiceAdapter implements CustomerUpdateService {
    private final TransactionalOperator transactionalOperator;
    private final PersonCustomerService personCustomerService;

    @Override
    public Mono<Void> putCustomer(String documentNumber, PutCustomerDto putCustomerDto) {
        return transactionalOperator.transactional(findPersonByDocumentNumber(documentNumber)
                .switchIfEmpty(Mono.error(new DatabaseSavingOperationException()))
                .flatMap(personDto -> updatePersonAndCustomer(personDto, putCustomerDto))
                .then()
        ).doOnError(error -> log.error("Transaction failed, performing rollback: {}", error.getMessage()));
    }

    private Mono<Void> updatePersonAndCustomer(PersonEntityDto personEntityDto, PutCustomerDto putCustomerDto) {
        return updatePerson(personEntityDto, putCustomerDto)
                .flatMap(updatedPersonEntity -> updateCustomerStatus(updatedPersonEntity, putCustomerDto));
    }

    private Mono<PersonEntity> updatePerson(PersonEntityDto personEntityDto, PutCustomerDto putCustomerDto) {
        return savePerson(CustomerMapper.INSTANCE.mapperToPutCustomerRequest(personEntityDto, putCustomerDto))
                .doOnSuccess(success -> log.info("Success in updating the person's data: {}", success))
                .doOnError(error -> log.error("Error updating the person's data: {}", error.getMessage()));
    }

    private Mono<Void> updateCustomerStatus(PersonEntity personEntity, PutCustomerDto putCustomerDto) {
        return findCustomerByPersonId(personEntity.getPersonId())
                .flatMap(customerDto -> updateCustomer(personEntity, putCustomerDto));
    }

    private Mono<PersonEntityDto> findPersonByDocumentNumber(String documentNumber) {
        return personCustomerService.findPersonByDocumentNumber(documentNumber);
    }

    private Mono<PersonEntity> savePerson(PersonEntity personEntity) {
        return personCustomerService.savePerson(personEntity);
    }

    private Mono<CustomerEntityDto> findCustomerByPersonId(Long personId) {
        return personCustomerService.findCustomerByPersonId(personId);
    }

    private Mono<Void> updateCustomer(PersonEntity personEntity, PutCustomerDto putCustomerDto) {
        return personCustomerService.updateCustomer(personEntity, putCustomerDto);
    }
}

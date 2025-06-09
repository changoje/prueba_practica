package com.nttdata.technicaltest.services.aplication.input.port;

import com.nttdata.technicaltest.services.domain.dto.CustomerEntityDto;
import com.nttdata.technicaltest.services.domain.dto.PersonEntityDto;
import com.nttdata.technicaltest.services.domain.dto.PutCustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.CustomerEntity;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import reactor.core.publisher.Mono;

public interface PersonCustomerService {
    Mono<PersonEntityDto> findPersonByDocumentNumber(String documentNumber);

    Mono<PersonEntity> savePerson(PersonEntity personEntity);

    Mono<Void> saveCustomer(CustomerEntity customerEntity);

    Mono<CustomerEntityDto> findCustomerByPersonId(Long personId);

    Mono<CustomerEntityDto> validateUser(String userName);

    Mono<Void> updateCustomer(PersonEntity personEntity, PutCustomerDto putCustomerDto);
}

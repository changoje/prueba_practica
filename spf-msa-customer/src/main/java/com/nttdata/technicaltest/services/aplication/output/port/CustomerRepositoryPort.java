package com.nttdata.technicaltest.services.aplication.output.port;

import com.nttdata.technicaltest.services.domain.dto.CustomerEntityDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.CustomerEntity;
import reactor.core.publisher.Mono;

public interface CustomerRepositoryPort {

    Mono<CustomerEntityDto> findByPersonIdAndStatus(Long personId, String status);

    Mono<CustomerEntityDto> findByPersonId(Long personId);

    Mono<CustomerEntityDto> validUser(String userName);

    Mono<Void> deleteByPersonId(Long personId);

    Mono<Void> saveCustomer(CustomerEntity customerEntity);

    Mono<Void> updateCustomer(String status, Long personId);
}

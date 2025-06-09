package com.nttdata.technicaltest.services.infrastructure.output.repository;

import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerEntityRepository extends ReactiveCrudRepository<CustomerEntity, Long> {
    Mono<CustomerDto> findByPersonId(Long personId);
}

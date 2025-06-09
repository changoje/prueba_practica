package com.nttdata.technicaltest.services.infrastructure.output.repository;

import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerEntityRepository extends ReactiveCrudRepository<CustomerEntity, Long> {
}

package com.nttdata.technicaltest.services.infrastructure.output.repository;

import com.nttdata.technicaltest.services.domain.dto.PersonEntityDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PersonEntityRepository extends ReactiveCrudRepository<PersonEntity, Long> {
    Mono<PersonEntityDto> findByDocumentNumber(String documentNumber);
}

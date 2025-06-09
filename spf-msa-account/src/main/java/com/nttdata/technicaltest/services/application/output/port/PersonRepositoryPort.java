package com.nttdata.technicaltest.services.application.output.port;

import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import reactor.core.publisher.Mono;

public interface PersonRepositoryPort {
    Mono<PersonEntity> findByDocumentNumber(String documentNumber);
}

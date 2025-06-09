package com.nttdata.technicaltest.services.aplication.output.port;

import com.nttdata.technicaltest.services.domain.dto.PersonEntityDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import reactor.core.publisher.Mono;

public interface PersonRepositoryPort {
    Mono<PersonEntityDto> findByDocumentNumber(String documentNumber);

    Mono<PersonEntity> save(PersonEntity personEntity);
}

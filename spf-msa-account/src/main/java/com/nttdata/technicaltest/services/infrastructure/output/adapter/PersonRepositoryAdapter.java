package com.nttdata.technicaltest.services.infrastructure.output.adapter;

import com.nttdata.technicaltest.services.application.output.port.PersonRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.output.repository.PersonEntityRepository;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryAdapter implements PersonRepositoryPort {
    private final PersonEntityRepository personEntityRepository;

    @Override
    public Mono<PersonEntity> findByDocumentNumber(String documentNumber) {
        return personEntityRepository.findByDocumentNumber(documentNumber);
    }
}

package com.nttdata.technicaltest.services.infrastructure.output.adapter;

import com.nttdata.technicaltest.services.aplication.output.port.PersonRepositoryPort;
import com.nttdata.technicaltest.services.domain.dto.PersonEntityDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.PersonEntityRepository;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Repository
@Slf4j
public class PersonRepositoryAdapter implements PersonRepositoryPort {
    private final PersonEntityRepository personEntityRepository;
    @Override
    public Mono<PersonEntityDto> findByDocumentNumber(String documentNumber) {
        return personEntityRepository.findByDocumentNumber(documentNumber);
    }

    @Override
    public Mono<PersonEntity> save(PersonEntity personEntity) {
        return personEntityRepository.save(personEntity);
    }
}

package com.nttdata.technicaltest.services.infrastructure.output.adapter;

import com.nttdata.technicaltest.services.application.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.CustomerEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {
    private final CustomerEntityRepository customerEntityRepository;

    @Override
    public Mono<CustomerDto> findByPersonId(Long personId) {
        return customerEntityRepository.findByPersonId(personId);
    }
}

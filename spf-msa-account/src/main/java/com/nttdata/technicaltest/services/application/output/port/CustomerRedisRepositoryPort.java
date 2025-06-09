package com.nttdata.technicaltest.services.application.output.port;

import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface CustomerRedisRepositoryPort {
    Mono<Void> deleteCustomer();

    Mono<CustomerDto> find(String uuid);
}

package com.nttdata.technicaltest.services.aplication.output.port;

import com.nttdata.technicaltest.services.domain.dto.CustomerCacheDto;
import reactor.core.publisher.Mono;

public interface CustomerRedisRepositoryPort {
    Mono<Void> sendDataToAsync(CustomerCacheDto customerCacheDto);
    Mono<Void> saveGetCustomer(CustomerCacheDto customerDto);
}

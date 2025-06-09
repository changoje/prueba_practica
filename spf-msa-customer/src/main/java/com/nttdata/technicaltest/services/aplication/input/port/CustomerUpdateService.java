package com.nttdata.technicaltest.services.aplication.input.port;

import com.nttdata.technicaltest.services.domain.dto.PutCustomerDto;
import reactor.core.publisher.Mono;

public interface CustomerUpdateService {
    Mono<Void> putCustomer(String documentNumber, PutCustomerDto putCustomerRequest);
}

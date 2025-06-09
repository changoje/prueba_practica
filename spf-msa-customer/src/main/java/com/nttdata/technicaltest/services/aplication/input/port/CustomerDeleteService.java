package com.nttdata.technicaltest.services.aplication.input.port;

import reactor.core.publisher.Mono;

public interface CustomerDeleteService {
    Mono<Void> deleteCustomer(String documentNumber);
}

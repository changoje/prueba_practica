package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface PersonCustomerService {
    Mono<CustomerDto> getCustomer(String documentNumber);
}

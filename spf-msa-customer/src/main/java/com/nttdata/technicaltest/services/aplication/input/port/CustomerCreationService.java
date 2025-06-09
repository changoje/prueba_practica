package com.nttdata.technicaltest.services.aplication.input.port;

import com.nttdata.technicaltest.services.domain.dto.PostCustomerDto;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Validated
public interface CustomerCreationService {

    Mono<Void> postCustomerCreate(@Valid PostCustomerDto postCustomerCreateDto, String authorization);
}

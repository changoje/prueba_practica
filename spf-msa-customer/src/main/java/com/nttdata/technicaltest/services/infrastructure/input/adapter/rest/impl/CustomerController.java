package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.impl;

import com.nttdata.technicaltest.services.model.GetCustomerSearchResponse;
import com.nttdata.technicaltest.services.model.PostCustomerCreateRequest;
import com.nttdata.technicaltest.services.model.PutCustomerRequest;
import com.nttdata.technicaltest.services.server.CustomersApi;
import com.nttdata.technicaltest.services.aplication.input.port.CustomerCreationService;
import com.nttdata.technicaltest.services.aplication.input.port.CustomerDeleteService;
import com.nttdata.technicaltest.services.aplication.input.port.CustomerSearchService;
import com.nttdata.technicaltest.services.aplication.input.port.CustomerUpdateService;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper.ControllerCustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {
    private final CustomerCreationService customerCreationService;
    private final CustomerUpdateService customerUpdateService;
    private final CustomerDeleteService customerDeleteService;
    private final CustomerSearchService customerSearchService;

    @Override
    public Mono<ResponseEntity<Void>> postCustomerCreate(String authorization, Mono<PostCustomerCreateRequest> postCustomerCreateRequest, ServerWebExchange exchange) {
        return postCustomerCreateRequest
                .flatMap(postCustomerCreateRequest1 -> customerCreationService.postCustomerCreate(ControllerCustomerMapper.INSTANCE.mapperToCustomerPostDTO(postCustomerCreateRequest1), authorization))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<GetCustomerSearchResponse>>> getCustomerSearch(String documentNumber, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(HttpStatus.OK)
                .body(customerSearchService.getCustomerSearch(documentNumber)));    }

    @Override
    public Mono<ResponseEntity<Void>> putCustomer(String documentNumber, Mono<PutCustomerRequest> putCustomerRequest, ServerWebExchange exchange) {
        return putCustomerRequest
                .flatMap(putCustomerRequest1 -> customerUpdateService.putCustomer(documentNumber, ControllerCustomerMapper.INSTANCE.mapperToCustomerPutDTO(putCustomerRequest1)))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String documentNumber, ServerWebExchange exchange) {
        return customerDeleteService.deleteCustomer(documentNumber)
                .map(ResponseEntity::ok);
    }
}

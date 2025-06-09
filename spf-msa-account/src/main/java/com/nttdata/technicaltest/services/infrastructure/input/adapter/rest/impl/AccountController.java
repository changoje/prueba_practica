package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.impl;

import com.nttdata.technicaltest.services.model.*;
import com.nttdata.technicaltest.services.server.AccountsApi;
import com.nttdata.technicaltest.services.application.input.port.FacadeTransactionsService;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.BuilderObject;
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
public class AccountController implements AccountsApi {

    private final FacadeTransactionsService facadeTransactionsService;

    @Override
    public Mono<ResponseEntity<Void>> postCustomerAccount(Mono<PostCustomerAccountRequest> postCustomerAccountRequest, ServerWebExchange exchange) {
        return postCustomerAccountRequest
                .flatMap(facadeTransactionsService::postCustomerAccount)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<GetCustomerAccountResponse>> getCustomerAccount(String documentNumber, ServerWebExchange exchange) {
        return facadeTransactionsService.getCustomerAccount(BuilderObject.parametersBuilder(documentNumber, null, null, null))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomerAccount(String documentNumber, String accountType, ServerWebExchange exchange) {
        return facadeTransactionsService.deleteCustomerAccount(BuilderObject.parametersBuilder(documentNumber, accountType, null, null))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> patchCustomerAccount(String documentNumber, String accountType, Mono<PatchCustomerAccountRequest> patchCustomerAccountRequest, ServerWebExchange exchange) {
        return patchCustomerAccountRequest
                .flatMap(patchCustomerAccountRequest1 -> facadeTransactionsService.patchCustomerAccount(BuilderObject.parametersBuilder(documentNumber, accountType, null, null), patchCustomerAccountRequest1))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> postCustomerTransactions(Mono<PostCustomerTransactionsRequest> postCustomerTransactionsRequest, ServerWebExchange exchange) {
        return postCustomerTransactionsRequest
                .flatMap(facadeTransactionsService::postCustomerTransactions)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerTransactions>>> getCustomerTransaction(String documentNumber, String startDate, String endDate, String accountType, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(HttpStatus.OK)
                .body(facadeTransactionsService.getCustomerTransaction(BuilderObject.parametersBuilder(documentNumber, accountType, startDate, endDate)))
        );
    }
}

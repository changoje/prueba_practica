package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.model.PostCustomerTransactionsRequest;
import reactor.core.publisher.Mono;

public interface AccountTransactionsService {
    Mono<Void> postCustomerTransactions(PostCustomerTransactionsRequest postCustomerTransactionsRequest);

}

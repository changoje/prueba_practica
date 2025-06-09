package com.nttdata.technicaltest.services.application.input.port;

import com.nttdata.technicaltest.services.domain.ParametersBuilder;
import com.nttdata.technicaltest.services.model.CustomerTransactions;
import reactor.core.publisher.Flux;

public interface AccountTransactionsSearchService {
    Flux<CustomerTransactions> getCustomerTransaction(ParametersBuilder parametersBuilder);
}

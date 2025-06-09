package com.nttdata.technicaltest.services.aplication.input.port;

import com.nttdata.technicaltest.services.model.GetCustomerSearchResponse;
import reactor.core.publisher.Flux;

public interface CustomerSearchService {
    Flux<GetCustomerSearchResponse> getCustomerSearch(String documentNumber);
}

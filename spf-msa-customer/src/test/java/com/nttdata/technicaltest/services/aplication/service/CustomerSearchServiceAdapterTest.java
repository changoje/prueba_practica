package com.nttdata.technicaltest.services.aplication.service;

import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.output.repository.PersonEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CustomerSearchServiceAdapterTest {
    @InjectMocks
    CustomerSearchServiceAdapter customerSearchService;

    @Mock
    PersonEntityRepository personEntityRepository;
    @Mock
    CustomerRepositoryPort customerRepositoryPort;


    @Test
    void getCustomerInformation() {
        Mockito.when(customerRepositoryPort.findByPersonIdAndStatus(Mockito.anyLong(), Mockito.anyString())).thenReturn(Mono.just(MockData.customerDto()));
        Mockito.when(personEntityRepository.findByDocumentNumber(Mockito.anyString())).thenReturn(Mono.just(MockData.personDto()));

        StepVerifier
                .create(customerSearchService.getCustomerSearch("2345345678"))
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(customerRepositoryPort, Mockito.times(1)).findByPersonIdAndStatus(Mockito.anyLong(), Mockito.anyString());
        Mockito.verify(personEntityRepository, Mockito.times(1)).findByDocumentNumber(Mockito.anyString());
    }

    @Test
    void getAllCustomerInformation() {
        Mockito.when(customerRepositoryPort.findByPersonIdAndStatus(Mockito.anyLong(), Mockito.anyString())).thenReturn(Mono.just(MockData.customerDto()));
        Mockito.when(personEntityRepository.findAll()).thenReturn(Flux.just(MockData.entityPerson()));

        StepVerifier
                .create(customerSearchService.getCustomerSearch(""))
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(customerRepositoryPort, Mockito.times(1)).findByPersonIdAndStatus(Mockito.anyLong(), Mockito.anyString());
        Mockito.verify(personEntityRepository, Mockito.times(1)).findAll();
    }
}

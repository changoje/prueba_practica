package com.nttdata.technicaltest.services.aplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.model.PutCustomerRequest;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.output.repository.PersonEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient
@SpringBootTest
class CustomerUpdateServiceAdapterIntegrationTest {

    @MockBean
    PersonEntityRepository personEntityRepository;
    @MockBean
    CustomerRepositoryPort customerRepositoryPort;

    @MockBean
    TransactionalOperator transactionalOperator;

    @BeforeEach
    public void setUp() {
        Mockito.when(transactionalOperator.transactional(Mockito.any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testIntegrationUpdateCustomer() throws JsonProcessingException {
        Mockito.when(personEntityRepository.findByDocumentNumber(Mockito.anyString())).thenReturn(Mono.just(MockData.personDto()));
        Mockito.when(personEntityRepository.save(Mockito.any())).thenReturn(Mono.just(MockData.entityPerson()));
        Mockito.when(customerRepositoryPort.findByPersonId(Mockito.anyLong())).thenReturn(Mono.just(MockData.customerDto()));
        Mockito.when(customerRepositoryPort.updateCustomer(Mockito.anyString(), Mockito.anyLong())).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/customers?documentNumber=1716456725")
                .body(Mono.just(MockData.buildPutCustomerUpdateReq()), PutCustomerRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Void.class);

        Mockito.verify(personEntityRepository, Mockito.times(1)).findByDocumentNumber(Mockito.anyString());
        Mockito.verify(personEntityRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(customerRepositoryPort, Mockito.times(1)).findByPersonId(Mockito.anyLong());
        Mockito.verify(customerRepositoryPort, Mockito.times(1)).updateCustomer(Mockito.anyString(), Mockito.anyLong());
    }

    @Test
    void testIntegrationUpdateCustomerError() throws JsonProcessingException {
        webTestClient.put()
                .uri("/customers")
                .body(Mono.just(MockData.buildPutCustomerUpdateRequest()), PutCustomerRequest.class)
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody(Void.class);
    }
}

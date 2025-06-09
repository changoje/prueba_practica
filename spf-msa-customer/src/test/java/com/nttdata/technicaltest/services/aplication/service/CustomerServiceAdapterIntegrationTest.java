package com.nttdata.technicaltest.services.aplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.model.PostCustomerCreateRequest;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRedisRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.output.repository.PersonEntityRepository;
import com.nttdata.technicaltest.services.aplication.input.port.JwtService;
import com.nttdata.technicaltest.services.domain.dto.CustomerEntityDto;
import io.jsonwebtoken.Claims;
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

import static org.mockito.Mockito.mock;

@AutoConfigureWebTestClient
@SpringBootTest
class CustomerServiceAdapterIntegrationTest {
    @MockBean
    CustomerRedisRepositoryPort customerRedisRepositoryPort;

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    TransactionalOperator transactionalOperator;

    @MockBean
    PersonEntityRepository personEntityRepository;

    @MockBean
    CustomerRepositoryPort customerRepositoryPort;

    @MockBean
    JwtService jwtService;

    @BeforeEach
    public void setUp() {
        Mockito.when(transactionalOperator.transactional(Mockito.any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testIntegrationCreateCustomer() throws JsonProcessingException {
        Claims claimsMock = createMockClaims();
        Mockito.when(personEntityRepository.findByDocumentNumber(Mockito.anyString())).thenReturn(Mono.empty());
        Mockito.when(personEntityRepository.save(Mockito.any())).thenReturn(Mono.just(MockData.entityPerson()));
        Mockito.when(customerRepositoryPort.findByPersonId(Mockito.anyLong())).thenReturn(Mono.just(MockData.customerDto()));
        Mockito.when(customerRepositoryPort.saveCustomer(Mockito.any())).thenReturn(Mono.empty());
        Mockito.when(customerRedisRepositoryPort.sendDataToAsync(Mockito.any())).thenReturn(Mono.empty());
        Mockito.when(jwtService.getClaimsFromToken(Mockito.anyString()))
                .thenReturn(Mono.just(claimsMock));
        Mockito.when(customerRepositoryPort.validUser(Mockito.anyString()))
                .thenReturn(Mono.just(mock(CustomerEntityDto.class)));
        Mockito.when(customerRedisRepositoryPort.saveGetCustomer(Mockito.any()))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/customers")
                .body(Mono.just(MockData.buildPostCustomerCreateRequestI()), PostCustomerCreateRequest.class)
                .header("authorization", "Bearer tuTokenDeAutorizacion")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

        Mockito.verify(personEntityRepository, Mockito.times(1)).findByDocumentNumber(Mockito.anyString());
        Mockito.verify(personEntityRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(customerRepositoryPort, Mockito.times(1)).findByPersonId(Mockito.anyLong());
        Mockito.verify(customerRepositoryPort, Mockito.times(1)).saveCustomer(Mockito.any());
    }

    @Test
    void testIntegrationCreateCustomerError() throws JsonProcessingException {
        webTestClient.post()
                .uri("/customers")
                .body(Mono.just(MockData.buildPostCustomerCreateRequestIe()), PostCustomerCreateRequest.class)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(Void.class);
    }

    private Claims createMockClaims() {
        Claims claimsMock = mock(Claims.class);
        Mockito.when(claimsMock.getSubject()).thenReturn("testUser");
        return claimsMock;
    }
}

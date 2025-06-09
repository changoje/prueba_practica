package com.nttdata.technicaltest.services.aplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.technicaltest.services.infrastructure.exception.CustomerExistException;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRedisRepositoryPort;
import com.nttdata.technicaltest.services.aplication.input.port.JwtService;
import com.nttdata.technicaltest.services.aplication.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.domain.dto.CustomerEntityDto;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CustomerCreationServiceAdapterTest {

    @InjectMocks
    CustomerCreationServiceAdapter customerService;
    @Mock
    PersonCustomerService personCustomerService;
    @Mock
    CustomerRedisRepositoryPort customerRedisRepositoryPort;
    @Mock
    TransactionalOperator transactionalOperator;
    @Mock
    JwtService jwtService;

    @BeforeEach
    public void setUp() {
        Mockito.when(transactionalOperator.transactional(Mockito.any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testPostCustomerCreate() throws JsonProcessingException {
        Claims claimsMock = createMockClaims();

        mockRepositoriesForPostCustomerCreateSuccess(claimsMock);

        StepVerifier.create(customerService.postCustomerCreate(MockData.buildPostCustomerCreateRequest(), getMockToken()))
                .expectNextCount(0)
                .verifyComplete();

        verifyPostCustomerCreateInteractions();
    }

    @Test
    void testPostCustomerCreateError() throws JsonProcessingException {
        Claims claimsMock = createMockClaims();

        Mockito.when(personCustomerService.findPersonByDocumentNumber(Mockito.anyString()))
                .thenReturn(Mono.just(MockData.personDto()));
        Mockito.when(personCustomerService.savePerson(Mockito.any()))
                .thenReturn(Mono.error(new RuntimeException()));
        Mockito.when(jwtService.getClaimsFromToken(Mockito.anyString()))
                .thenReturn(Mono.just(claimsMock));
        Mockito.when(personCustomerService.validateUser(Mockito.anyString()))
                .thenReturn(Mono.just(mock(CustomerEntityDto.class)));

        StepVerifier.create(customerService.postCustomerCreate(MockData.buildPostCustomerCreateRequest(), getMockToken()))
                .expectError(CustomerExistException.class)
                .verify();

        verifyPostCustomerCreateErrorInteractions();
    }

    private Claims createMockClaims() {
        Claims claimsMock = mock(Claims.class);
        Mockito.when(claimsMock.getSubject()).thenReturn("testUser");
        return claimsMock;
    }

    private void mockRepositoriesForPostCustomerCreateSuccess(Claims claimsMock) {
        Mockito.when(personCustomerService.findPersonByDocumentNumber(Mockito.anyString()))
                .thenReturn(Mono.empty());
        Mockito.when(personCustomerService.savePerson(Mockito.any()))
                .thenReturn(Mono.just(MockData.entityPerson()));
        Mockito.when(personCustomerService.findCustomerByPersonId(Mockito.anyLong()))
                .thenReturn(Mono.just(MockData.customerDto()));
        Mockito.when(personCustomerService.saveCustomer(Mockito.any()))
                .thenReturn(Mono.empty());
        Mockito.when(customerRedisRepositoryPort.sendDataToAsync(Mockito.any()))
                .thenReturn(Mono.empty());
        Mockito.when(customerRedisRepositoryPort.saveGetCustomer(Mockito.any()))
                .thenReturn(Mono.empty());
        Mockito.when(jwtService.getClaimsFromToken(Mockito.anyString()))
                .thenReturn(Mono.just(claimsMock));
        Mockito.when(personCustomerService.validateUser(Mockito.anyString()))
                .thenReturn(Mono.just(mock(CustomerEntityDto.class)));
    }

    private void verifyPostCustomerCreateInteractions() {
        Mockito.verify(personCustomerService, Mockito.times(1))
                .findPersonByDocumentNumber(Mockito.anyString());
        Mockito.verify(personCustomerService, Mockito.times(1))
                .savePerson(Mockito.any());
        Mockito.verify(personCustomerService, Mockito.times(1))
                .findCustomerByPersonId(Mockito.anyLong());
        Mockito.verify(personCustomerService, Mockito.times(1))
                .saveCustomer(Mockito.any());
    }

    private void verifyPostCustomerCreateErrorInteractions() {
        Mockito.verify(personCustomerService, Mockito.times(1))
                .findPersonByDocumentNumber(Mockito.anyString());
        Mockito.verify(personCustomerService, Mockito.times(1))
                .savePerson(Mockito.any());
    }

    private String getMockToken() {
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqY2hhbmdvOTkiLCJ1dWlkIjoiN2Q1MDU2NTQtYTdlYi00Mzk3LTk4MzctMmVhMGEzMGNjYWVkIiwiaWF0IjoxNzQxODQxNjE2LCJleHAiOjI3NDE4MjM2MTZ9.-El0mXnOaEE1sg9nPtjQxXyGT1ITQ-HUAHsD-yvxclY";
    }
}

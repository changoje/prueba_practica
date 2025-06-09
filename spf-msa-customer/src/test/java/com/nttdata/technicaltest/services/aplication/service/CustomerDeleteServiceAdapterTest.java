package com.nttdata.technicaltest.services.aplication.service;

import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.output.repository.PersonEntityRepository;
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

@ExtendWith(MockitoExtension.class)
class CustomerDeleteServiceAdapterTest {
    @InjectMocks
    CustomerDeleteServiceAdapter customerDeleteService;
    @Mock
    PersonEntityRepository personEntityRepository;
    @Mock
    CustomerRepositoryPort customerRepositoryPort;
    @Mock
    TransactionalOperator transactionalOperator;

    @BeforeEach
    public void setUp() {
        Mockito.when(transactionalOperator.transactional(Mockito.any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void deleteCustomer() {
        Mockito.when(personEntityRepository.findByDocumentNumber(Mockito.anyString())).thenReturn(Mono.just(MockData.personDto()));
        Mockito.when(customerRepositoryPort.deleteByPersonId(Mockito.anyLong())).thenReturn(Mono.empty());
        Mockito.when(personEntityRepository.deleteById(Mockito.anyLong())).thenReturn(Mono.empty());
        StepVerifier
                .create(customerDeleteService.deleteCustomer("2365897896"))
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(personEntityRepository, Mockito.times(1)).findByDocumentNumber(Mockito.anyString());
        Mockito.verify(customerRepositoryPort, Mockito.times(1)).deleteByPersonId(Mockito.anyLong());
        Mockito.verify(personEntityRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}
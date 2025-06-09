package com.nttdata.technicaltest.services.application.service.strategy;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.application.output.port.AccountTransactionRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.exception.AmountAllowException;
import com.nttdata.technicaltest.services.infrastructure.exception.AmountMultipleException;
import com.nttdata.technicaltest.services.infrastructure.exception.AvailableBalanceException;
import com.nttdata.technicaltest.services.infrastructure.exception.DebitException;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountTransactionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DebitByCardAdapterTest {

    @Mock
    private AccountTransactionRepositoryPort accountTransactionEntityRepository;

    @Mock
    private AccountRepositoryPort accountEntityRepository;

    @InjectMocks
    private DebitByCardAdapter debitOperation;

    private AccountEntity accountEntity;

    @BeforeEach
    public void setUp() {
        accountEntity = new AccountEntity();
        accountEntity.setAvailableBalance(BigDecimal.valueOf(500));
        accountEntity.setAccountId(123456L);
    }

    @Test
    void execute_shouldProcessTransaction_whenBalanceIsValidAndAmountIsMultipleOfTen() {
        when(accountTransactionEntityRepository.save(any(AccountTransactionEntity.class)))
                .thenReturn(Mono.just(new AccountTransactionEntity()));
        when(accountEntityRepository.save(any(AccountEntity.class)))
                .thenReturn(Mono.just(accountEntity));

        Mono<Void> result = debitOperation.execute(accountEntity, 50.0, MockData.transactions());

        StepVerifier.create(result)
                .verifyComplete();

        verify(accountTransactionEntityRepository, times(1)).save(any(AccountTransactionEntity.class));
        verify(accountEntityRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    void execute_shouldReturnError_whenBalanceIsInsufficient() {
        accountEntity.setAvailableBalance(BigDecimal.valueOf(30));

        Mono<Void> result = debitOperation.execute(accountEntity, 50.0, MockData.transactions());

        StepVerifier.create(result)
                .expectError(AvailableBalanceException.class)
                .verify();
    }

    @Test
    void execute_shouldReturnError_whenAmountIsNotMultipleOfTen() {
        Mono<Void> result = debitOperation.execute(accountEntity, 55.0, MockData.transactions());

        StepVerifier.create(result)
                .expectError(AmountMultipleException.class)
                .verify();
    }

    @Test
    void execute_shouldReturnError_whenAmountExceedsLimit() {
        Mono<Void> result = debitOperation.execute(accountEntity, 350.0, MockData.transactions());

        StepVerifier.create(result)
                .expectError(AmountAllowException.class)
                .verify();
    }

    @Test
    void processTransaction_shouldReturnError_whenSavingTransactionFails() {
        when(accountTransactionEntityRepository.save(any(AccountTransactionEntity.class)))
                .thenReturn(Mono.error(new RuntimeException()));

        Mono<Void> result = debitOperation.execute(accountEntity, 50.0, MockData.transactions());

        StepVerifier.create(result)
                .expectError(DebitException.class)
                .verify();
    }
}
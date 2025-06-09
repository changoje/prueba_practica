package com.nttdata.technicaltest.services.application.service.strategy;

import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.application.output.port.AccountTransactionRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.exception.ApplicationException;
import com.nttdata.technicaltest.services.infrastructure.exception.CreditException;
import com.nttdata.technicaltest.services.mock.MockData;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountTransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditByDepositAdapterTest {

    @Mock
    private AccountTransactionRepositoryPort transactionRepository;

    @Mock
    private AccountRepositoryPort accountRepository;

    @InjectMocks
    private CreditByDepositAdapter creditOperation;

    @Test
    void execute_whenValidTransaction_shouldSaveTransactionAndUpdateAccount() {
        when(transactionRepository.save(any(AccountTransactionEntity.class)))
                .thenReturn(Mono.just(MockData.AccountTransactionEntity()));

        when(accountRepository.save(any(AccountEntity.class)))
                .thenReturn(Mono.just(MockData.mapperToAccountEntity()));

        Mono<Void> result = creditOperation.execute(MockData.mapperToAccountEntity(), 50.00, MockData.transactions());

        StepVerifier.create(result)
                .verifyComplete();

        verify(transactionRepository, times(1)).save(any(AccountTransactionEntity.class));
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    void execute_whenNegativeAmount_shouldThrowApplicationException() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAvailableBalance(BigDecimal.valueOf(100.00));

        assertThrows(ApplicationException.class, () -> creditOperation.execute(accountEntity, -50.00, MockData.transactions()).block());
    }

    @Test
    void execute_whenTransactionSaveFails_shouldThrowDatabaseSavingOperationException() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAvailableBalance(BigDecimal.valueOf(100.00));
        accountEntity.setAccountId(123L);

        when(transactionRepository.save(any(AccountTransactionEntity.class)))
                .thenReturn(Mono.error(new RuntimeException()));

        Mono<Void> result = creditOperation.execute(accountEntity, 50.00, MockData.transactions());

        StepVerifier.create(result)
                .expectError(CreditException.class)
                .verify();

        verify(transactionRepository, times(1)).save(any(AccountTransactionEntity.class));
        verify(accountRepository, never()).save(any(AccountEntity.class));
    }
}
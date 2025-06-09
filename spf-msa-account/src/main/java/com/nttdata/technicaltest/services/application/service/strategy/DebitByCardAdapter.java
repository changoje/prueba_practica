package com.nttdata.technicaltest.services.application.service.strategy;

import com.nttdata.technicaltest.services.application.input.port.Operation;
import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.application.output.port.AccountTransactionRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.exception.*;
import com.nttdata.technicaltest.services.model.Transactions;
import com.nttdata.technicaltest.services.application.service.decorator.DecoratorTransactionService;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ValidationFunctions.validateAndThrowIfInvalid;
import static com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ValidationFunctions.validateDebit;

@Service
@Slf4j
public class DebitByCardAdapter extends DecoratorTransactionService implements Operation {

    public DebitByCardAdapter(AccountTransactionRepositoryPort accountTransactionRepositoryPort, AccountRepositoryPort accountRepositoryPort) {
        super(accountTransactionRepositoryPort, accountRepositoryPort);
    }

    @Override
    public Mono<Void> execute(AccountEntity accountEntity, double amount, Transactions typeOperation) {

        try {
            validateDebit(accountEntity, amount);
        } catch (AvailableBalanceException | AmountAllowException | AmountMultipleException e) {
            log.error("Error, whe validate debit. -> {}", e.getMessage());
            return Mono.error(e);
        }

        return super.saveTransaction(accountEntity, amount, typeOperation, calculateNewBalance(accountEntity, amount))
                .onErrorMap(throwable -> new DebitException());
    }

    private BigDecimal calculateNewBalance(AccountEntity accountEntity, double amount) {
        BigDecimal debitAmount = BigDecimal.valueOf(amount);
        validateAndThrowIfInvalid(debitAmount);
        return accountEntity.getAvailableBalance().subtract(debitAmount);
    }
}

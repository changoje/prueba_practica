package com.nttdata.technicaltest.services.application.service.strategy;

import com.nttdata.technicaltest.services.application.input.port.Operation;
import com.nttdata.technicaltest.services.application.output.port.AccountRepositoryPort;
import com.nttdata.technicaltest.services.application.output.port.AccountTransactionRepositoryPort;
import com.nttdata.technicaltest.services.infrastructure.exception.CreditException;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ValidationFunctions;
import com.nttdata.technicaltest.services.model.Transactions;
import com.nttdata.technicaltest.services.application.service.decorator.DecoratorTransactionService;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.ValidationFunctions.validateAndThrowIfInvalid;

@Service
@Slf4j
public class CreditByDepositAdapter extends DecoratorTransactionService implements Operation {
    public CreditByDepositAdapter(AccountTransactionRepositoryPort accountTransactionRepositoryPort, AccountRepositoryPort accountRepositoryPort) {
        super(accountTransactionRepositoryPort, accountRepositoryPort);
    }

    @Override
    public Mono<Void> execute(AccountEntity accountEntity, double amount, Transactions typeOperation) {
        return super.saveTransaction(accountEntity, amount, typeOperation, calculateNewBalance(accountEntity, amount))
                .onErrorMap(throwable -> new CreditException());
    }

    private BigDecimal calculateNewBalance(AccountEntity accountEntity, double amount) {
        BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);
        validateAndThrowIfInvalid(amountBigDecimal);
        return accountEntity.getAvailableBalance().add(amountBigDecimal);
    }
}

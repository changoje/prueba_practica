package com.nttdata.technicaltest.services.application.service.factory;

import com.nttdata.technicaltest.services.application.input.port.FactoryTransactionService;
import com.nttdata.technicaltest.services.infrastructure.exception.InvalidTransactionException;
import com.nttdata.technicaltest.services.model.Transactions;
import com.nttdata.technicaltest.services.application.input.port.Operation;
import com.nttdata.technicaltest.services.application.service.strategy.CreditByDepositAdapter;
import com.nttdata.technicaltest.services.application.service.strategy.DebitByCardAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class FactoryTransactionServiceAdapter implements FactoryTransactionService {
    private final Map<String, Operation> operations = new HashMap<>();
    public static final String DEBIT = "DEBIT";
    public static final String CREDIT = "CREDIT";
    public static final String DEBIT_CARD = "DEBIT_CARD";
    public static final String CREDIT_DEPOSIT = "CREDIT_DEPOSIT";
    private final DebitByCardAdapter debitByCard;
    private final CreditByDepositAdapter creditByDeposit;

    @Override
    public Operation getOperation(Transactions transactions) {
        String transaction = transactions.getTransaction().getValue();
        String typeTransaction = transactions.getTypeTransaction().getValue();

        if (isValidCombination(transaction, typeTransaction)) {
            if (!operations.containsKey(typeTransaction)) {
                initializeOperation(typeTransaction);
            }
            return operations.get(typeTransaction);
        }
        log.warn("Invalid transaction details provided: transaction={} typeTransaction={}", transaction, typeTransaction);
        throw new InvalidTransactionException("Invalid transaction details provided");
    }

    private boolean isValidCombination(String transaction, String typeTransaction) {
        Map<String, List<String>> validCombinations = new HashMap<>();
        validCombinations.put(DEBIT, List.of(DEBIT_CARD));
        validCombinations.put(CREDIT, List.of(CREDIT_DEPOSIT));

        List<String> validTypes = validCombinations.get(transaction);
        return validTypes != null && validTypes.contains(typeTransaction);
    }

    private void initializeOperation(String type) {
        switch (type) {
            case DEBIT_CARD:
                operations.put(DEBIT_CARD, debitByCard);
                break;
            case CREDIT_DEPOSIT:
                operations.put(CREDIT_DEPOSIT, creditByDeposit);
                break;
            default:
        }
    }
}

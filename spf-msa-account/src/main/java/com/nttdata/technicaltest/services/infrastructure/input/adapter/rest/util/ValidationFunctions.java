package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util;

import com.nttdata.technicaltest.services.infrastructure.exception.AmountAllowException;
import com.nttdata.technicaltest.services.infrastructure.exception.AmountMultipleException;
import com.nttdata.technicaltest.services.infrastructure.exception.ApplicationException;
import com.nttdata.technicaltest.services.infrastructure.exception.AvailableBalanceException;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.Constants.*;
import static com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.Constants.DEBIT_AMOUNT;

public class ValidationFunctions {
    private ValidationFunctions() {

    }

    public static final Predicate<BigDecimal> isInvalidAmount = amount -> amount.compareTo(BigDecimal.ZERO) <= 0;
    public static final DoublePredicate isAmountMultiple = amount -> amount % MULTIPLE == 0;
    public static final DoublePredicate isAmountAllowed = amount -> amount <= DEBIT_AMOUNT;

    public static Predicate<AccountEntity> isSufficientBalance(double amount) {
        return accountEntity -> accountEntity.getAvailableBalance().compareTo(BigDecimal.valueOf(amount)) >= 0;
    }

    public static final Supplier<ApplicationException> exceptionSupplier = () -> new ApplicationException(CODE, VALID_AMOUNT, HttpStatus.CONFLICT);

    public static final Supplier<AvailableBalanceException> exceptionAvailableBalance = AvailableBalanceException::new;
    public static final Supplier<AmountMultipleException> exceptionAmountMultiple = AmountMultipleException::new;
    public static final Supplier<AmountAllowException> exceptionAmountAllow = AmountAllowException::new;

    public static void validateAndThrowIfInvalid(BigDecimal amount) {
        if (isInvalidAmount.test(amount)) {
            throw exceptionSupplier.get();
        }
    }

    public static void validateDebit(AccountEntity accountEntity, double amount) throws AvailableBalanceException, AmountAllowException, AmountMultipleException {
        if (!isSufficientBalance(amount).test(accountEntity)) {
            throw exceptionAvailableBalance.get();
        }

        if (!isAmountAllowed.test(amount)) {
            throw exceptionAmountAllow.get();
        }

        if (!isAmountMultiple.test(amount)) {
            throw exceptionAmountMultiple.get();
        }
    }
}

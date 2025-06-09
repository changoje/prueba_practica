package com.nttdata.technicaltest.services.infrastructure.exception;

public class AmountAllowException extends Exception {
    public AmountAllowException(){
        super("The amount is not allow for debit.");
    }
}

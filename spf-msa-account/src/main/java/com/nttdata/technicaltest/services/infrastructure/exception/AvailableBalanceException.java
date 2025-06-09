package com.nttdata.technicaltest.services.infrastructure.exception;

public class AvailableBalanceException extends Exception {
    public AvailableBalanceException(){
        super("The amount is greater than the available balance.");
    }
}

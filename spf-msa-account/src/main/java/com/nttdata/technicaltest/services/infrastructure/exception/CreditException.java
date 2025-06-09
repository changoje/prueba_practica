package com.nttdata.technicaltest.services.infrastructure.exception;

public class CreditException extends Exception {
    public CreditException(){
        super("The account cannot be accredit.");
    }
}

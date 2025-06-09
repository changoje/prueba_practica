package com.nttdata.technicaltest.services.infrastructure.exception;

public class AmountMultipleException extends Exception {
    public AmountMultipleException(){
        super("The amount to be debited entered is not a multiple of 10.");
    }
}

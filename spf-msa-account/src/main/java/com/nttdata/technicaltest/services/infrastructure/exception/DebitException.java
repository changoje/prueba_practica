package com.nttdata.technicaltest.services.infrastructure.exception;

public class DebitException extends Exception {
    public DebitException(){
        super("The account cannot be debited.");
    }
}

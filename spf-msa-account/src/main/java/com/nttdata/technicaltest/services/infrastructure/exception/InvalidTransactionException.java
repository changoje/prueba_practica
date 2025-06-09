package com.nttdata.technicaltest.services.infrastructure.exception;

public class InvalidTransactionException extends RuntimeException{
    public InvalidTransactionException(String message) {
        super(message);
    }
}

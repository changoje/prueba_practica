package com.nttdata.technicaltest.services.infrastructure.exception;

public class ExecuteOperationException extends Exception {
    public ExecuteOperationException(){
        super("The combination of transaction and transaction type do not exist.");
    }
}

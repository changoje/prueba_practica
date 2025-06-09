package com.nttdata.technicaltest.services.exception;

public class ClientAccountOperationException extends Exception {
    public ClientAccountOperationException(){
        super("Error running client to save account transaction");
    }
}

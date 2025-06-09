package com.nttdata.technicaltest.services.infrastructure.exception;

public class AccountNotExistException extends Exception {
    public AccountNotExistException(){
        super("The account not exist to transact");
    }
}

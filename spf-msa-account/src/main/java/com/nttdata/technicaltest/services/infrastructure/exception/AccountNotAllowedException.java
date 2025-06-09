package com.nttdata.technicaltest.services.infrastructure.exception;

public class AccountNotAllowedException extends Exception {
    public AccountNotAllowedException(){
        super("The account is not enabled to transact");
    }
}

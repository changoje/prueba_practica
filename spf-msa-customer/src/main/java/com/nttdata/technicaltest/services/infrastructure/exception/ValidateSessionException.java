package com.nttdata.technicaltest.services.infrastructure.exception;

public class ValidateSessionException extends Exception {
    public ValidateSessionException(){
        super("The credentials are incorrect!!");
    }
}

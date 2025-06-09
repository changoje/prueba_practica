package com.nttdata.technicaltest.services.infrastructure.exception;

public class ValidateIdentificationException extends Exception {
    public ValidateIdentificationException(){
        super("The document number is invalid");
    }
}

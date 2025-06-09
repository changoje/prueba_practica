package com.nttdata.technicaltest.services.infrastructure.exception;

public class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(){
        super("The amount is not allowed");
    }
}

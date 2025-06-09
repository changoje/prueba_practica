package com.nttdata.technicaltest.services.exception;

public class ClientNotFoundException extends CodeException {
    public ClientNotFoundException() {
        super(404, "The client was not found");
    }
}
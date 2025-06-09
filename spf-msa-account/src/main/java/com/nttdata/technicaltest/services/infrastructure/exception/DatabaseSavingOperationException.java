package com.nttdata.technicaltest.services.infrastructure.exception;

public class DatabaseSavingOperationException extends Exception {
    public DatabaseSavingOperationException(){
        super("Cannot complete database saving operation");
    }
}

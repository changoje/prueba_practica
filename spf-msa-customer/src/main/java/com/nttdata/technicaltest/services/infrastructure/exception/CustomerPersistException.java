

package com.nttdata.technicaltest.services.infrastructure.exception;

public class CustomerPersistException extends Exception {
    public CustomerPersistException(){
        super("The customer's information could not be saved..");
    }
}

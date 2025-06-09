

package com.nttdata.technicaltest.services.infrastructure.exception;

public class CustomerExistException extends Exception {
    public CustomerExistException(){
        super("You can't create a customer that already exists.");
    }
}

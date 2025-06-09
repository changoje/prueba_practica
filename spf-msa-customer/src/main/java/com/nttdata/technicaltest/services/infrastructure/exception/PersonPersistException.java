

package com.nttdata.technicaltest.services.infrastructure.exception;

public class PersonPersistException extends Exception {
    public PersonPersistException(){
        super("The person's information could not be saved..");
    }
}

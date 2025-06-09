package com.nttdata.technicaltest.services.infrastructure.exception;

public class JsonParsingException extends Exception {
    public JsonParsingException(){
        super("Error parsing JSON");
    }
}

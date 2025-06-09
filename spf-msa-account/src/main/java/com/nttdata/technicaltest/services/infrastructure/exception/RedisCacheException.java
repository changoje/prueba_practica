package com.nttdata.technicaltest.services.infrastructure.exception;

public class RedisCacheException extends Exception {
    public RedisCacheException(){
        super("Information not found in cache");
    }
}

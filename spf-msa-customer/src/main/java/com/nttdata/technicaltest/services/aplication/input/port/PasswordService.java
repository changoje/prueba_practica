package com.nttdata.technicaltest.services.aplication.input.port;


public interface PasswordService {
    String encryptPassword(String rawPassword);

    boolean matchesPassword(String rawPassword, String encodedPassword);
}

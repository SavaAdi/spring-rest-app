package com.adisavaspring.rest.springrestapp.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnoprstuvwxyz";

    public String generateUserId(int length){
        return generateRandomString(length);
    }

    private String generateRandomString(int length){
        StringBuilder returnedValue = new StringBuilder();
        for(int i=1; i<= length; i++)
            returnedValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));

        return new String(returnedValue);
    }
}

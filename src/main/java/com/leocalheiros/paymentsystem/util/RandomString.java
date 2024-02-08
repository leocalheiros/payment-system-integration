package com.leocalheiros.paymentsystem.util;

import java.security.SecureRandom;

public class RandomString {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString(int lenght){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < lenght; i++){
            int index = secureRandom.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}

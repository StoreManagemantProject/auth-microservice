package com.example.demo.util;

import at.favre.lib.crypto.bcrypt.BCrypt;


public class Authorization {
    public static final Integer SALT = 16;

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(SALT, password.toCharArray());
    }
}
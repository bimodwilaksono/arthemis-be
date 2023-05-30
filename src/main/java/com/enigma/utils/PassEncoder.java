package com.enigma.utils;

import de.mkammerer.argon2.Argon2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PassEncoder {
    private final Argon2 argon2;

    @Autowired
    public PassEncoder(Argon2 argon2) {
        this.argon2 = argon2;
    }

    public String hashPassword(String password){
        return argon2.hash(10,65536,1, password.toCharArray());
    }

    public boolean verifyPassword(String hash, String password){
        return argon2.verify(hash,password.toCharArray());
    }
}

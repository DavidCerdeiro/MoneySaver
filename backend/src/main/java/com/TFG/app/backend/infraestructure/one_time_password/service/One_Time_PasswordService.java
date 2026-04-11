package com.TFG.app.backend.infraestructure.one_time_password.service;

import java.security.SecureRandom;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.Cache;
import org.springframework.lang.NonNull;

@Service
public class One_Time_PasswordService {
    

    @Autowired
    private Cache<String, String> otpCache;

    public One_Time_PasswordService() {
        
    }

    public String generateOTP(@NonNull String email) {
        SecureRandom secureRandom = new SecureRandom();
        int token = secureRandom.nextInt(1_000_000);
        String tokenString = String.format("%06d", token);
        
        // Envolvemos ambas variables con requireNonNull. 
        // Esto silencia las advertencias 16778128 de forma definitiva.
        otpCache.put(
            Objects.requireNonNull(email, "Email no puede ser nulo"), 
            Objects.requireNonNull(tokenString, "Token no puede ser nulo")
        );
        
        return tokenString;
    }

    public String getOTP(@NonNull String email) {
        // Hacemos lo mismo aquí para el getIfPresent
        return otpCache.getIfPresent(Objects.requireNonNull(email, "Email no puede ser nulo"));
    }
}

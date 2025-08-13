package com.TFG.app.backend.infraestructure.one_time_password.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.Cache;

@Service
public class One_Time_PasswordService {
    

    @Autowired
    private Cache<String, String> otpCache;

    public One_Time_PasswordService() {
        
    }

    public String generateOTP(String email) {
        SecureRandom secureRandom = new SecureRandom();
        int token = secureRandom.nextInt(1_000_000);
        String tokenString = String.format("%06d", token);
        
        otpCache.put(email, tokenString);
        return tokenString;

    }

    public String getOTP(String email) {
        String otp = otpCache.getIfPresent(email);
        return otp != null ? otp : null;
    }
}

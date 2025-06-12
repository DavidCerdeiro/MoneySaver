package com.TFG.app.backend.infraestructure.otp.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;
import com.TFG.app.backend.infraestructure.otp.repository.One_Time_PasswordRepository;
import com.TFG.app.backend.infraestructure.otp.entity.One_Time_Password;
@Service
public class One_Time_PasswordService {
    
    
    private final One_Time_PasswordRepository oneTimePasswordRepository;

    public One_Time_PasswordService(One_Time_PasswordRepository oneTimePasswordRepository) {
        this.oneTimePasswordRepository = oneTimePasswordRepository;
    }

    public String generateOTP(String email) {
        
        SecureRandom secureRandom = new SecureRandom();
        int token = secureRandom.nextInt(1_000_000);
        String tokenString = String.format("%06d", token);
        One_Time_Password otp = new One_Time_Password();
        otp.setEmail(email);
        otp.setToken(tokenString);
        otp.setExpiration(new java.sql.Timestamp(System.currentTimeMillis() + 10 * 60 * 1000));
        oneTimePasswordRepository.save(otp);
        return tokenString;

    }
}

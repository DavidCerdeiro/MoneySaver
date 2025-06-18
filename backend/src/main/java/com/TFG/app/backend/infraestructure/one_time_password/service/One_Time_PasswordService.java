package com.TFG.app.backend.infraestructure.one_time_password.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.infraestructure.one_time_password.entity.One_Time_Password;
import com.TFG.app.backend.infraestructure.one_time_password.repository.One_Time_PasswordRepository;
import com.TFG.app.backend.infraestructure.purpose_otp.service.Purpose_OTPService;

import java.util.Optional;


import java.util.List;
@Service
public class One_Time_PasswordService {
    
    
    private final One_Time_PasswordRepository oneTimePasswordRepository;

    private final Purpose_OTPService purposeOTPService;

    public One_Time_PasswordService(One_Time_PasswordRepository oneTimePasswordRepository, Purpose_OTPService purposeOTPService) {
        this.oneTimePasswordRepository = oneTimePasswordRepository;
        this.purposeOTPService = purposeOTPService;
    }

    public String generateOTP(String email, String purpose) {
        
        SecureRandom secureRandom = new SecureRandom();
        int token = secureRandom.nextInt(1_000_000);
        String tokenString = String.format("%06d", token);
        One_Time_Password otp = new One_Time_Password();
        otp.setEmail(email);
        otp.setPurposeOTP(purposeOTPService.getPurposeOTP(purpose));
        otp.setToken(tokenString);
        otp.setExpiration(new java.sql.Timestamp(System.currentTimeMillis() + 10 * 60 * 1000));
        oneTimePasswordRepository.save(otp);
        return tokenString;

    }

    public One_Time_Password getOTP(String email) {
        Optional<One_Time_Password> otp = oneTimePasswordRepository.findByEmail(email);
        if(otp.isPresent()) {
            One_Time_Password oneTimePassword = otp.get();
            if(oneTimePassword.getExpiration().after(new java.sql.Timestamp(System.currentTimeMillis()))) {
                System.out.println("OTP found: " + oneTimePassword.getToken() + " for email: " + email);
                return oneTimePassword;
            } else {
                System.out.println("OTP expired for email: " + email);
                oneTimePasswordRepository.delete(oneTimePassword);
            }
        }
        return null;
    }

    public void deleteExpiredOTPs() {
        List<One_Time_Password> expiredOTPs = oneTimePasswordRepository.findByExpirationBefore(new java.sql.Timestamp(System.currentTimeMillis()));
        if (!expiredOTPs.isEmpty()) {
            System.out.println("Deleting expired OTPs: " + expiredOTPs.size());
            oneTimePasswordRepository.deleteAll(expiredOTPs);
        }
    }

    public void deleteUsedOTP(Integer Id) {
        oneTimePasswordRepository.deleteById(Id);
    }
}

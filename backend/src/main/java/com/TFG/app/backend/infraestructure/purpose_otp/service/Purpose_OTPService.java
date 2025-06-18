package com.TFG.app.backend.infraestructure.purpose_otp.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.infraestructure.purpose_otp.entity.Purpose_OTP;
import com.TFG.app.backend.infraestructure.purpose_otp.repository.Purpose_OTPRepository;

@Service
public class Purpose_OTPService {

    private final Purpose_OTPRepository purposeOTPRepository;
    public Purpose_OTPService(Purpose_OTPRepository purposeOTPRepository) {
        this.purposeOTPRepository = purposeOTPRepository;
    }

    public Integer getPurposeOTPId(String purpose) {
        return purposeOTPRepository.findByName(purpose).getId();
    }

    public Purpose_OTP getPurposeOTP(String purpose) {
        return purposeOTPRepository.findByName(purpose);
    }
}

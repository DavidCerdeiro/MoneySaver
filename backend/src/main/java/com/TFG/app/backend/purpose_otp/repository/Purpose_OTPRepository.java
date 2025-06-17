package com.TFG.app.backend.purpose_otp.repository;

import com.TFG.app.backend.purpose_otp.entity.Purpose_OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Purpose_OTPRepository extends JpaRepository<Purpose_OTP, Integer> {
    public Purpose_OTP findByName(String name);
} 
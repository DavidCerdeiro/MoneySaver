package com.TFG.app.backend.infraestructure.purpose_otp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.infraestructure.purpose_otp.entity.Purpose_OTP;

public interface Purpose_OTPRepository extends JpaRepository<Purpose_OTP, Integer> {
    public Purpose_OTP findByName(String name);
} 
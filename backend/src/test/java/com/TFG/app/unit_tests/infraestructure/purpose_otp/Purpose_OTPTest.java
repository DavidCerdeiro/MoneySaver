package com.TFG.app.unit_tests.infraestructure.purpose_otp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.TFG.app.backend.infraestructure.purpose_otp.entity.Purpose_OTP;
public class Purpose_OTPTest {
    @Test
    public void testPurposeOTPAttributes() {
        Purpose_OTP purposeOTP = new Purpose_OTP();
        purposeOTP.setName("Test Purpose");

        Assertions.assertEquals("Test Purpose", purposeOTP.getName());
    }
}

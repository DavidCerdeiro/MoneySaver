package com.TFG.app.unit_tests.infraestructure.one_time_password;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.TFG.app.backend.infraestructure.one_time_password.entity.One_Time_Password;
import com.TFG.app.backend.infraestructure.purpose_otp.entity.Purpose_OTP;

public class One_Time_PasswordTest {
    @Test
    public void testOneTimePasswordAttributes() {
        One_Time_Password oneTimePassword = new One_Time_Password();
        Purpose_OTP purposeOTP = new Purpose_OTP();

        purposeOTP.setName("Test Purpose");
        oneTimePassword.setEmail("example@email.com");
        oneTimePassword.setToken("123456");
        oneTimePassword.setExpiration(new java.sql.Timestamp(System.currentTimeMillis()));
        oneTimePassword.setPurposeOTP(purposeOTP);

        Assertions.assertEquals("example@email.com", oneTimePassword.getEmail());
        Assertions.assertEquals("123456", oneTimePassword.getToken());
        Assertions.assertNotNull(oneTimePassword.getExpiration());
        Assertions.assertEquals("Test Purpose", oneTimePassword.getPurposeOTP().getName());
    }
}

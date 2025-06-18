package com.TFG.app.backend.infraestructure.one_time_password.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

import com.TFG.app.backend.infraestructure.purpose_otp.entity.Purpose_OTP;

@Entity
@Table(name = "one_time_password",
    uniqueConstraints = @UniqueConstraint(columnNames = {"Email", "Id_PurposeOTP"})
)
public class One_Time_Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Id_PurposeOTP", nullable = false)
    private Purpose_OTP purposeOTP;

    @Column(name = "Email", length = 32, nullable = false)
    private String email;

    @Column(name = "Token", length = 6, nullable = false)
    private String token;

    @Column(name = "Expiration", nullable = false)
    private Timestamp expiration;

    //Getters and Setters
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }
    
    public Purpose_OTP getPurposeOTP() {
        return purposeOTP;
    }
    public void setPurposeOTP(Purpose_OTP purposeOTP) {
        this.purposeOTP = purposeOTP;
    }
}

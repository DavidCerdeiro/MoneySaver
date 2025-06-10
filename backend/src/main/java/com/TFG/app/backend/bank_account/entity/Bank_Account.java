package com.TFG.app.backend.bank_account.entity;

import jakarta.persistence.*;

import com.TFG.app.backend.user.entity.User;

@Entity
@Table(name = "bank_account")
public class Bank_Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = false)
    private User user;

    @Column(name = "BankName", length = 32, nullable = false)
    private String bankName;

    @Column(name = "AccessToken", length = 128, nullable = false, unique = true)
    private String accessToken;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

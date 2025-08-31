package com.TFG.app.backend.account.entity;

import jakarta.persistence.*;

import com.TFG.app.backend.user.entity.User;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = false)
    private User user;

    @Column(name = "TrueLayerId", length = 32, nullable = false, unique = true)
    private String trueLayerId;

    @Column(name = "Name", length = 64, nullable = false)
    private String name;

    @Column(name = "BankName", length = 32, nullable = false)
    private String bankName;

    @Column(name = "Number", length = 128, nullable = false)
    private String number;

    // Getters and Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTrueLayerId() {
        return trueLayerId;
    }
    public void setTrueLayerId(String trueLayerId) {
        this.trueLayerId = trueLayerId;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}

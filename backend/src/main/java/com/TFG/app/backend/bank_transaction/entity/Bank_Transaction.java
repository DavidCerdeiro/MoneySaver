package com.TFG.app.backend.bank_transaction.entity;

import jakarta.persistence.*;

import com.TFG.app.backend.bank_account.entity.Bank_Account;
import com.TFG.app.backend.spending.entity.Spending;

@Entity
@Table(name = "bank_transaction")
public class Bank_Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "Id_BankAccount", nullable = false)
    private Bank_Account BankAccount;

    @OneToOne
    @JoinColumn(name = "Id_Spending", unique = true, nullable = false)
    private Spending spending;

    // Getters and Setters
    public Integer getId() {
        return Id;
    }

    public Bank_Account getBankAccount() {
        return BankAccount;
    }
    public void setBankAccount(Bank_Account bankAccount) {
        BankAccount = bankAccount;
    }

    public Spending getSpending() {
        return spending;
    }
    public void setSpending(Spending spending) {
        this.spending = spending;
    }

}

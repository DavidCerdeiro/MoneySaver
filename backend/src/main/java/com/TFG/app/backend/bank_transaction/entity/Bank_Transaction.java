package com.TFG.app.backend.bank_transaction.entity;

import jakarta.persistence.*;

import com.TFG.app.backend.bank_account.entity.Bank_Account;
import com.TFG.app.backend.spending.entity.Spending;


public class Bank_Transaction {
    /*"Id" serial  PRIMARY KEY,
  "Id_BankAccount" int NOT NULL,
  "Id_Spending" int NOT NULL UNIQUE,
  FOREIGN KEY ("Id_Spending") REFERENCES spending("Id"),
  FOREIGN KEY ("Id_BankAccount") REFERENCES "bank_account"("Id") */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "Id_BankAccount", nullable = false)
    private Bank_Account BankAccount;

    @OneToOne
    @JoinColumn(name = "Id_Spending", unique = true, nullable = false)
    private Spending spending;

    // Getters and Setters
    public Long getId() {
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

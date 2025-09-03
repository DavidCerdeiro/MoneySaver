package com.TFG.app.backend.transaction.entity;

import jakarta.persistence.*;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.spending.entity.Spending;

@Entity
@Table(
    name = "transaction",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"Id_Account", "TrueLayerId"})
    }
)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "Id_Account", nullable = false)
    private Account account;

    @OneToOne
    @JoinColumn(name = "Id_Spending", unique = true, nullable = false)
    private Spending spending;

    @Column(name = "TransactionCode", nullable = false, length = 64, unique = true)
    private String transactionCode;

    // Getters and Setters
    public Integer getId() {
        return Id;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public Spending getSpending() {
        return spending;
    }
    public void setSpending(Spending spending) {
        this.spending = spending;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}

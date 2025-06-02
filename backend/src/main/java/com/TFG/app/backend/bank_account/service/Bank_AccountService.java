package com.TFG.app.backend.bank_account.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.bank_account.repository.Bank_AccountRepository;
import com.TFG.app.backend.bank_account.entity.Bank_Account;

@Service
public class Bank_AccountService {
    private final Bank_AccountRepository bankAccountRepository;

    public Bank_AccountService(Bank_AccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public Bank_Account createBankAccount(Bank_Account bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }    
}

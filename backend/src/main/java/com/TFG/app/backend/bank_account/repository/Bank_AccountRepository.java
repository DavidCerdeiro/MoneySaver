package com.TFG.app.backend.bank_account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TFG.app.backend.bank_account.entity.Bank_Account;

public interface Bank_AccountRepository extends JpaRepository<Bank_Account, Integer> {
    
}

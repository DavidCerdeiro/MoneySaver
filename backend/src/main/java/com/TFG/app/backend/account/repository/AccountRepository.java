package com.TFG.app.backend.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    
}

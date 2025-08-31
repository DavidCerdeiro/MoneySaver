package com.TFG.app.backend.account.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.account.repository.AccountRepository;
import com.TFG.app.backend.user.entity.User;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> findByUser(User user) {
        List<Account> accounts = accountRepository.findByUser(user);
        return accounts;
    }

    public boolean existByUserAndTrueLayerId(User user, String trueLayerId) {
        return accountRepository.existsByUserAndTrueLayerId(user, trueLayerId);
    }

    public Account findById(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    public boolean deleteAccount(Integer id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

package com.TFG.app.backend.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.user.entity.User;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByUser(User user);
}

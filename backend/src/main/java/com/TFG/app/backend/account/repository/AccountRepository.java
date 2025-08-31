package com.TFG.app.backend.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.user.entity.User;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findByUser(User user);

    boolean existsByUser(User user);

    boolean existsByUserAndTrueLayerId(User user, String trueLayerId);
}

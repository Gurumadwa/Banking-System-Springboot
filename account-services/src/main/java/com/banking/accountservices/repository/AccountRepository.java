package com.banking.accountservices.repository;

import com.banking.accountservices.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByEmail(String email);
    boolean existsByAccountNumber(String accountNumber);
}

package com.banking.accountservices.dto;

import com.banking.accountservices.entity.AccountStatus;
import com.banking.accountservices.entity.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountResponse {

    private String id;

    private String accountNumber;

    private String accountHolderName;

    private String email;

    private String phone;

    private AccountType accountType;

    private AccountStatus status;

    private BigDecimal balance;

    private BigDecimal dailyTransactionLimit;

    private LocalDateTime createdAt;
}

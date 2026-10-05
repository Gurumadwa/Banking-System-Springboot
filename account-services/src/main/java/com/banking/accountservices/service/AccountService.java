package com.banking.accountservices.service;

import com.banking.accountservices.dto.AccountResponse;
import com.banking.accountservices.dto.CreateAccountRequest;
import com.banking.accountservices.entity.Account;
import com.banking.accountservices.entity.AccountStatus;
import com.banking.accountservices.entity.AccountType;
import com.banking.accountservices.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    public AccountResponse createAccount(CreateAccountRequest request) {

        log.info("Creating account for: {}", request.getEmail());

        if(accountRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Account already exists for email: " + request.getEmail());

        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setAccountHolderName(request.getAccountHolderName());
        account.setPhone(request.getPhone());
        account.setAccountType(request.getAccountType());
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(request.getInitialDeposit()); //initially balance is initial deposit.
        account.setAccountNumber(generateAccountNumber()); //generate unique acc. number

        account.setDailyTransactionLimit(
                request.getAccountType() == AccountType.SAVINGS
                ? new BigDecimal("100000") : new BigDecimal("500000")
        );

        Account savedAccount = accountRepository.save(account);
        log.info("Account Created: {} for {}",savedAccount.getAccountNumber(),savedAccount.getEmail());

        return mapToResponse(savedAccount);
    }

    //generate unique 12 digit account number
    private String generateAccountNumber() {
        String accountNumber;

        do{
            long number = secureRandom.nextLong(1_000_000_000_000L);
            accountNumber = String.format("%012d",number);
        }while(accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    public AccountResponse getAccount(String accountNumber) {
    return null;
    }

    public BigDecimal getBalance(String accountNumber) {
    return null;
    }

    public void blockAccount(String accountNumber) {
    }

    public void deductBalance(String accountNumber, BigDecimal amount) {
    }

    public void creditBalance(String accountNumber, BigDecimal amount) {
    }

    private AccountResponse mapToResponse(Account account) {
         AccountResponse response = new AccountResponse();
         response.setId(account.getId());
         response.setAccountNumber(account.getAccountNumber());
         response.setAccountHolderName(account.getAccountHolderName());
         response.setEmail(account.getEmail());
         response.setPhone(account.getPhone());
         response.setAccountType(account.getAccountType());
         response.setBalance(account.getBalance());
         response.setDailyTransactionLimit(account.getDailyTransactionLimit());
         response.setStatus(account.getStatus());
         response.setCreatedAt(account.getCreatedAt());

         return response;
    }
}

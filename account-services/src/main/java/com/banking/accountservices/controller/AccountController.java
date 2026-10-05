package com.banking.accountservices.controller;

import com.banking.accountservices.dto.AccountResponse;
import com.banking.accountservices.dto.CreateAccountRequest;
import com.banking.accountservices.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber){
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String accountNumber){
        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }

    @PutMapping("/{accountNumber}/block")
    public ResponseEntity<String> blockAccount(@PathVariable String accountNumber){
        accountService.blockAccount(accountNumber);

        return ResponseEntity.ok("Account blocked");
    }

    /**
     * SAGA STEP 1 - Deduct Balance
     * called by transaction service when transfer is initiated
     */
    @PutMapping("/{accountNumber}/deduct")
    public ResponseEntity<String> deductBalance(@PathVariable String accountNumber,
                                                @RequestParam BigDecimal amount){
        accountService.deductBalance(accountNumber, amount);
        return ResponseEntity.ok("Account deducted");
    }

    /**
     * SAGA SETP 4 - Compensating Transaction endpoint
     * Called by Transaction Service in Two Scenarios:
     * 1. Fraud Detected - refund the sender(undo step 1 i.e. deductBalance)
     * 2. Transaction Completed - Credit Receiver
     */
    @PutMapping("/{accountNumber}/credit")
    public ResponseEntity<String> creditBalance(@PathVariable String accountNumber,
                                                @RequestParam BigDecimal amount){
        accountService.creditBalance(accountNumber, amount);
        return ResponseEntity.ok("Account Credited");
    }
}

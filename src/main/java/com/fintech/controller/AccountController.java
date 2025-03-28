package com.fintech.controller;

import com.fintech.dto.OpenAccountRequest;
import com.fintech.dto.TransactionRequest;
import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
public AccountController(AccountService accountService){
    this.accountService = accountService;
}
    @PostMapping
    public ResponseEntity<Long> openAccount(@RequestBody OpenAccountRequest request) {
        Account account = accountService.openAccount(request.getOwnerName(), request.getInitialBalance());
        return ResponseEntity.ok(account.getId());
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Long> deposit(@PathVariable Long accountId, @RequestBody TransactionRequest request) {
        Transaction transaction = accountService.deposit(accountId, request.getAmount());
        return ResponseEntity.ok(transaction.getId());
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Long> withdraw(@PathVariable Long accountId, @RequestBody TransactionRequest request) {
        Transaction transaction = accountService.withdraw(accountId, request.getAmount());
        return ResponseEntity.ok(transaction.getId());
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountId) {
        BigDecimal balance = accountService.getBalance(accountId);
        return ResponseEntity.ok(balance);
    }

}

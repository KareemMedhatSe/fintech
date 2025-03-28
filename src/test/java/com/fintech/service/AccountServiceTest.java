package com.fintech.service;

import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.TransactionType;
import com.fintech.repository.AccountRepository;
import com.fintech.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        accountService = new AccountService(accountRepository, transactionRepository);
    }

    @Test
    void testOpenAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setOwnerName("Kareem");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.openAccount("Kareem", new BigDecimal("1000"));

        assertEquals("Kareem", result.getOwnerName());
        assertEquals(new BigDecimal("1000"), result.getBalance());
        assertEquals(1L, result.getId());
    }

    @Test
    void testDeposit() {
        Long accountId = 1L;
        BigDecimal depositAmount = new BigDecimal("500");
        Account account = new Account();
        account.setId(accountId);
        account.setOwnerName("Test");
        account.setBalance(new BigDecimal("1000"));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = accountService.deposit(accountId, depositAmount);

        assertEquals(TransactionType.DEPOSIT, transaction.getType());
        assertEquals(depositAmount, transaction.getAmount());
        assertEquals(new BigDecimal("1500"), account.getBalance());
    }

    @Test
    void testWithdraw() {
        Long accountId = 1L;
        BigDecimal withdrawAmount = new BigDecimal("200");
        Account account = new Account();
        account.setId(accountId);
        account.setOwnerName("Test");
        account.setBalance(new BigDecimal("500"));


        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = accountService.withdraw(accountId, withdrawAmount);

        assertEquals(TransactionType.WITHDRAW, transaction.getType());
        assertEquals(new BigDecimal("300"), account.getBalance());
    }

    @Test
    void testWithdraw_InsufficientBalance() {
        Long accountId = 1L;
        BigDecimal withdrawAmount = new BigDecimal("1000");
        Account account = new Account();
        account.setId(accountId);
        account.setOwnerName("Test");
        account.setBalance(new BigDecimal("500"));


        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(accountId, withdrawAmount);
        });
    }

    @Test
    void testGetBalance() {
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(new BigDecimal("750"));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        BigDecimal balance = accountService.getBalance(accountId);

        assertEquals(new BigDecimal("750"), balance);
    }

    @Test
    void testGetBalance_AccountNotFound() {
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            accountService.getBalance(accountId);
        });
    }
}

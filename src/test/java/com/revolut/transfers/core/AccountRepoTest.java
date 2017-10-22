package com.revolut.transfers.core;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class AccountRepoTest {

    private static final String SOME_ACCOUNT_ID = "some-account-id";
    private AccountRepo accountRepo;

    @Before
    public void setUp() throws Exception {
        accountRepo = new AccountRepo();
        accountRepo.add(new Account(SOME_ACCOUNT_ID, new BigDecimal("200.00")));
    }

    @Test
    public void shouldReturnTrueIfAccountExists() throws Exception {
        assertTrue(accountRepo.hasAccount(SOME_ACCOUNT_ID));
    }

    @Test
    public void shouldCreditBalanceAccordingly() throws Exception {
        BigDecimal creditAmount = new BigDecimal("20.00");

        BigDecimal remainingBalance = accountRepo.updateBalance(SOME_ACCOUNT_ID, creditAmount);
        BigDecimal expectedRemainingBalance = accountRepo.getBalance(SOME_ACCOUNT_ID);

        assertThat(remainingBalance, is(expectedRemainingBalance));
    }

    @Test
    public void shouldDebitBalanceAccordingly() throws Exception {
        BigDecimal debitAmount = new BigDecimal("20.00").negate();

        BigDecimal remainingBalance = accountRepo.updateBalance(SOME_ACCOUNT_ID, debitAmount);
        BigDecimal expectedRemainingBalance = accountRepo.getBalance(SOME_ACCOUNT_ID);;
        assertThat(remainingBalance, is(expectedRemainingBalance));
    }
}
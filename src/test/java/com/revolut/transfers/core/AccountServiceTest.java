package com.revolut.transfers.core;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    private static final AccountRepo accountRepo = mock(AccountRepo.class);
    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        accountService = new AccountService(accountRepo);
    }

    @Test
    public void shouldReturnTrueIfBothAccountsExists() throws Exception {
        when(accountRepo.hasAccount(SENDER_ACCOUNT_ID)).thenReturn(true);
        when(accountRepo.hasAccount(RECEIVER_ACCOUNT_ID)).thenReturn(true);

        boolean accountsExist = accountService.hasAccounts(Arrays.asList(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID));
        assertTrue(accountsExist);
    }

    @Test
    public void shouldReturnFalseIfAnyAccountDoesNotExist() throws Exception {
        when(accountRepo.hasAccount(SENDER_ACCOUNT_ID)).thenReturn(true);
        when(accountRepo.hasAccount(RECEIVER_ACCOUNT_ID)).thenReturn(false);

        boolean accountsExist = accountService.hasAccounts(Arrays.asList(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID));
        assertFalse(accountsExist);
    }

    @Test
    public void shouldReturnRemainingBalanceAfterAnUpdateToBalance() throws Exception {
        BigDecimal debitAmount = new BigDecimal("20.00");
        BigDecimal expectedRemainingBalance = new BigDecimal("180.00");
        when(accountRepo.updateBalance(SENDER_ACCOUNT_ID, debitAmount)).thenReturn(expectedRemainingBalance);

        BigDecimal actualRemainingBalance = accountService.updateBalance(SENDER_ACCOUNT_ID, debitAmount);

        assertThat(actualRemainingBalance, is(expectedRemainingBalance));
    }
}
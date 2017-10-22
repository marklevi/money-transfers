package com.revolut.transfers.core;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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

        boolean accountsExist = accountService.accountsExist(Arrays.asList(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID));
        assertTrue(accountsExist);
    }

    @Test
    public void shouldReturnFalseIfAnyAccountDoesNotExist() throws Exception {
        when(accountRepo.hasAccount(SENDER_ACCOUNT_ID)).thenReturn(true);
        when(accountRepo.hasAccount(RECEIVER_ACCOUNT_ID)).thenReturn(false);

        boolean accountsExist = accountService.accountsExist(Arrays.asList(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID));
        assertFalse(accountsExist);
    }
}
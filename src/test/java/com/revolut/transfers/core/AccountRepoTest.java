package com.revolut.transfers.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AccountRepoTest {

    private static final String SOME_ACCOUNT_ID = "some-account-id";
    private AccountRepo accountRepo;

    @Before
    public void setUp() throws Exception {
        accountRepo = new AccountRepo();
        accountRepo.addAccount(new Account(SOME_ACCOUNT_ID));
    }

    @Test
    public void shouldReturnTrueIfAccountExists() throws Exception {
        assertTrue(accountRepo.hasAccount(SOME_ACCOUNT_ID));
    }

}
package com.revolut.transfers.core.account;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class AccountRepoTest {

    private static final String SOME_ACCOUNT_ID = "some-account-id";
    private AccountRepo accountRepo;

    @Before
    public void setUp() throws Exception {
        accountRepo = new AccountRepo();
        accountRepo.addAccount(new Account(SOME_ACCOUNT_ID, new BigDecimal("0.00")));
    }

    @Test
    public void shouldReturnTrueIfAccountExists() throws Exception {
        assertTrue(accountRepo.hasAccount(SOME_ACCOUNT_ID));
    }

}
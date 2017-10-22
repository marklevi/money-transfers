package com.revolut.transfers.core;

import java.math.BigDecimal;

public class Account {
    private final String accountId;
    private final BigDecimal balance;
    private final boolean allowOverdraft;

    public Account(String accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
        this.allowOverdraft = false;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}

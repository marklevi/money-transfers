package com.revolut.transfers.core;

import java.math.BigDecimal;

public class Account {
    private final String accountId;
    private BigDecimal balance;
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

    public BigDecimal updateBalance(BigDecimal amount) {
        BigDecimal newBalance = balance.add(amount);
        balance = newBalance;
        return newBalance;
    }
}

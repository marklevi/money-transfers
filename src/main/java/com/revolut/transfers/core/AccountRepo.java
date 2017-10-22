package com.revolut.transfers.core;

import java.math.BigDecimal;
import java.util.HashMap;

public class AccountRepo {

    private final HashMap<String, Account> dataStore;

    public AccountRepo() {
        dataStore = new HashMap<>();
    }

    public boolean hasAccount(String accountId) {
        return dataStore.containsKey(accountId);
    }

    public Account add(Account account) {
        return dataStore.put(account.getAccountId(), account);
    }

    public BigDecimal updateBalance(String accountId, BigDecimal amount) {
        Account account = dataStore.get(accountId);
        return account.updateBalance(amount);
    }

    public BigDecimal getBalance(String accountId) {
        Account account = dataStore.get(accountId);
        return account.getBalance();
    }
}

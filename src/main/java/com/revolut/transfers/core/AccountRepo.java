package com.revolut.transfers.core;

import java.util.HashMap;
import java.util.Map;

public class AccountRepo {

    private final Map<String, Account> dataStore;

    public AccountRepo() {
        dataStore = new HashMap<>();
    }

    public boolean hasAccount(String accountId) {
        return dataStore.containsKey(accountId);
    }

    public Account addAccount(Account account) {
        return dataStore.put(account.getAccountId(), account);
    }

    public Account getAccount(String accountId) {
        return dataStore.get(accountId);
    }
}

package com.revolut.transfers.core;

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
}

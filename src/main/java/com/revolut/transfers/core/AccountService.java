package com.revolut.transfers.core;

import java.util.List;

public class AccountService {

    private AccountRepo dataStore;

    public AccountService(AccountRepo dataStore) {
        this.dataStore = dataStore;
    }

    public boolean accountsExist(List<String> accountIds) {
        return false;
    }
}

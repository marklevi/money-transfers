package com.revolut.transfers.core;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    private AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public boolean hasAccounts(List<String> accountIds) {
        return accountIds.stream().allMatch((id) -> accountRepo.hasAccount(id));
    }

    public BigDecimal updateBalance(String accountId, BigDecimal amount) {
        return null;
    }
}

package com.revolut.transfers.core;

import com.revolut.transfers.utils.StructuralEquivalence;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

public class Account extends StructuralEquivalence {
    private final String accountId;
    private final Collection<Entry> ledger;
    private final boolean allowOverdraft;

    public Account(String accountId) {
        this.accountId = accountId;
        this.ledger = new HashSet<>();
        this.allowOverdraft = false;
    }

    public String getAccountId() {
        return accountId;
    }

    public Collection<Entry> getLedger() {
        return ledger;
    }

    public Entry addEntry(Entry entry) {
        ledger.add(entry);
        return entry;
    }

    public BigDecimal getBalance() {
        return ledger
                .stream()
                .map(Entry::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}

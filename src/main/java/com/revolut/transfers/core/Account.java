package com.revolut.transfers.core;

import com.revolut.transfers.utils.StructuralEquivalence;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

public class Account extends StructuralEquivalence {
    private final String accountId;
    private BigDecimal overDraftLimit;
    private final Collection<Entry> ledger;

    public Account(String accountId, BigDecimal overDraftLimit) {
        this.accountId = accountId;
        this.overDraftLimit = overDraftLimit;
        this.ledger = new HashSet<>();
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

    public BigDecimal getAvailableBalance() {
        return this.getBalance().add(overDraftLimit);
    }

}

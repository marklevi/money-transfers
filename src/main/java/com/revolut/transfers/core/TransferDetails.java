package com.revolut.transfers.core;

import com.revolut.transfers.utils.StructuralEquivalence;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferDetails extends StructuralEquivalence {
    private final Account senderAccount;
    private final Account receiverAccount;
    private final BigDecimal amount;
    private final String description;
    private final LocalDate date;

    public TransferDetails(Account senderAccount, Account receiverAccount, BigDecimal amount, String description) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.description = description;
        this.date = LocalDate.now();
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }
}

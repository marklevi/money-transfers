package com.revolut.transfers.core.transfer;

import com.revolut.transfers.core.account.Account;
import com.revolut.transfers.utils.StructuralEquivalence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Transfer extends StructuralEquivalence {
    private final Account senderAccount;
    private final Account receiverAccount;
    private final BigDecimal amount;
    private final String description;
    private final String id;
    private final LocalDate createdAt;

    public Transfer(NewTransfer newTransfer) {
        senderAccount = newTransfer.getSenderAccount();
        receiverAccount = newTransfer.getReceiverAccount();
        amount = newTransfer.getAmount();
        description = newTransfer.getDescription();
        id = UUID.randomUUID().toString();
        createdAt = LocalDate.now();

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

    public String getId() {
        return id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}

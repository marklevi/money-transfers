package com.revolut.transfers.core;

import com.revolut.transfers.utils.StructuralEquivalence;

import java.math.BigDecimal;

public class TransferDetails extends StructuralEquivalence {
    private final String senderAccountId;
    private final String receiverAccountId;
    private final BigDecimal amount;
    private final String description;

    public TransferDetails(String senderAccountId, String receiverAccountId, BigDecimal amount, String description) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.description = description;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public String getReceiverAccountId() {
        return receiverAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}

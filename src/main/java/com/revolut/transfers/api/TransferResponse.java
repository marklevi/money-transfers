package com.revolut.transfers.api;

import com.revolut.transfers.core.transfer.Transfer;

import java.math.BigDecimal;

public class TransferResponse {

    private String id;
    private String sender;
    private String receiver;
    private BigDecimal amount;
    private String description;
    private String createdAt;

    private TransferResponse() {
        /* For Jackson */
    }

    public TransferResponse(Transfer transfer) {
        this.id = transfer.getId();
        this.sender = transfer.getSenderAccount().getAccountId();
        this.receiver = transfer.getReceiverAccount().getAccountId();
        this.amount = transfer.getAmount();
        this.description = transfer.getDescription();
        this.createdAt = transfer.getCreatedAt().toString();
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}

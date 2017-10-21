package com.revolut.transfers;

import org.hibernate.validator.constraints.NotBlank;

public class TransferDetailsRequest {

    @NotBlank
    private String sender;

    @NotBlank
    private String receiver;

    @NotBlank
    private String amount;

    private String description;

    private TransferDetailsRequest() {
        /* For Jackson */
    }

    public TransferDetailsRequest(String sender, String receiver, String amount, String description) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.description = description;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}

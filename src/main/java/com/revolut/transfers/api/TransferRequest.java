package com.revolut.transfers.api;

import com.revolut.transfers.utils.StructuralEquivalence;
import org.hibernate.validator.constraints.NotBlank;

public class TransferRequest extends StructuralEquivalence {

    @NotBlank
    private String sender;

    @NotBlank
    private String receiver;

    @NotBlank
    private String amount;

    private String description;

    private TransferRequest() {
        /* For Jackson */
    }

    public TransferRequest(String sender, String receiver, String amount, String description) {
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

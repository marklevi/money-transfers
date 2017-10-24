package com.revolut.transfers.api;

import com.revolut.transfers.utils.StructuralEquivalence;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class TransferRequest extends StructuralEquivalence {

    @NotBlank
    private String sender;

    @NotBlank
    private String receiver;

    @DecimalMin(value = "0.00")
    private BigDecimal amount;

    private String description;

    private TransferRequest() {
        /* For Jackson */
    }

    public TransferRequest(String sender, String receiver, BigDecimal amount, String description) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}

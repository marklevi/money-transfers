package com.revolut.transfers.core.transfer;

import com.revolut.transfers.core.account.Account;
import com.revolut.transfers.utils.StructuralEquivalence;

import java.math.BigDecimal;

public class NewTransfer extends StructuralEquivalence {
    private final Account senderAccount;
    private final Account receiverAccount;
    private final BigDecimal amount;
    private final String description;

    public NewTransfer(Account senderAccount, Account receiverAccount, BigDecimal amount, String description) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.description = description;
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

}

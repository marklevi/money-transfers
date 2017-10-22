package com.revolut.transfers.core;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferService {
    private AccountService accountService;

    public TransferService(AccountService accountService) {
        this.accountService = accountService;
    }

    public String makeTransfer(TransferDetails transferDetails) {
        String senderAccountId = transferDetails.getSenderAccountId();
        String receiverAccountId = transferDetails.getReceiverAccountId();
        BigDecimal amount = transferDetails.getAmount();

        accountService.updateBalance(senderAccountId, amount.negate());
        accountService.updateBalance(receiverAccountId, amount);
        return UUID.randomUUID().toString();
    }
}

package com.revolut.transfers.core;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferService {

    private TransferRepo transferRepo;

    public TransferService(TransferRepo transferRepo) {
        this.transferRepo = transferRepo;
    }

    public String makeTransfer(TransferDetails transferDetails) {
        Account senderAccount = transferDetails.getSenderAccount();
        Account receiverAccount = transferDetails.getReceiverAccount();
        BigDecimal amount = transferDetails.getAmount();
        LocalDate date = transferDetails.getDate();

        addEntryToAccount(senderAccount, amount.negate(), date);
        addEntryToAccount(receiverAccount, amount, date);

        return transferRepo.saveRecord(transferDetails);
    }

    private Entry addEntryToAccount(Account account, BigDecimal amount, LocalDate date){
        Entry entry = new Entry(amount, date);
        return account.addEntry(entry);
    }
}

package com.revolut.transfers.core;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferService {

    private TransferRepo transferRepo;

    public TransferService(TransferRepo transferRepo) {
        this.transferRepo = transferRepo;
    }

    public String preformTransfer(Transfer transfer) {
        Account senderAccount = transfer.getSenderAccount();
        Account receiverAccount = transfer.getReceiverAccount();
        BigDecimal amount = transfer.getAmount();
        LocalDate date = transfer.getDate();

        addEntryToAccount(senderAccount, amount.negate(), date);
        addEntryToAccount(receiverAccount, amount, date);

        return transferRepo.addTransfer(transfer);
    }

    private Entry addEntryToAccount(Account account, BigDecimal amount, LocalDate date){
        Entry entry = new Entry(amount, date);
        return account.addEntry(entry);
    }
}

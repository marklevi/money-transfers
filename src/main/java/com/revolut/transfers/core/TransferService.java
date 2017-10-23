package com.revolut.transfers.core;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferService {

    private TransferRepo transferRepo;

    public TransferService(TransferRepo transferRepo) {
        this.transferRepo = transferRepo;
    }

    public String transfer(Transfer transfer) {
        Account senderAccount = transfer.getSenderAccount();
        Account receiverAccount = transfer.getReceiverAccount();
        BigDecimal amount = transfer.getAmount();
        LocalDate date = transfer.getDate();

        if(!canAffordTransfer(senderAccount, amount)){
            throw new InsufficientFundsException();
        }
        addEntryToAccount(senderAccount, amount.negate(), date);
        addEntryToAccount(receiverAccount, amount, date);

        return transferRepo.addTransfer(transfer);
    }

    private boolean canAffordTransfer(Account senderAccount, BigDecimal amount) {
        return senderAccount.getAvailableBalance().compareTo(amount) > 0;
    }

    private Entry addEntryToAccount(Account account, BigDecimal amount, LocalDate date){
        Entry entry = new Entry(amount, date);
        return account.addEntry(entry);
    }
}

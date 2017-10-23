package com.revolut.transfers.core;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferService {

    private TransferRepo transferRepo;

    public TransferService(TransferRepo transferRepo) {
        this.transferRepo = transferRepo;
    }

    public String transfer(NewTransfer newTransfer) {
        Account senderAccount = newTransfer.getSenderAccount();
        Account receiverAccount = newTransfer.getReceiverAccount();
        BigDecimal amount = newTransfer.getAmount();
        LocalDate date = newTransfer.getDate();

        if(!canAffordTransfer(senderAccount, amount)){
            throw new InsufficientFundsException();
        }
        addEntryToAccount(senderAccount, amount.negate(), date);
        addEntryToAccount(receiverAccount, amount, date);

        return transferRepo.addTransfer(newTransfer);
    }

    private boolean canAffordTransfer(Account senderAccount, BigDecimal amount) {
        return senderAccount.getAvailableBalance().compareTo(amount) > 0;
    }

    private Entry addEntryToAccount(Account account, BigDecimal amount, LocalDate date){
        Entry entry = new Entry(amount, date);
        return account.addEntry(entry);
    }
}

package com.revolut.transfers.core.transfer;

import com.revolut.transfers.core.account.Account;
import com.revolut.transfers.core.account.Entry;
import com.revolut.transfers.core.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.Optional;

public class TransferService {

    private TransferRepo transferRepo;
    private TransferMapper transferMapper;

    public TransferService(TransferRepo transferRepo, TransferMapper transferMapper) {
        this.transferRepo = transferRepo;
        this.transferMapper = transferMapper;
    }

    public Transfer transfer(NewTransfer newTransfer) {
        Account senderAccount = newTransfer.getSenderAccount();
        Account receiverAccount = newTransfer.getReceiverAccount();
        BigDecimal amount = newTransfer.getAmount();

        if (!canAffordTransfer(senderAccount, amount)) {
            throw new InsufficientFundsException();
        }

        addEntryToAccount(senderAccount, amount.negate());
        addEntryToAccount(receiverAccount, amount);

        Transfer transfer = transferMapper.mapFrom(newTransfer);
        return transferRepo.addTransfer(transfer);
    }

    public Optional<Transfer> getTransfer(String id) {
        return transferRepo.getTransfer(id);
    }

    public boolean hasNonce(String nonce) {
        return transferRepo.hasNonce(nonce);
    }

    private boolean canAffordTransfer(Account senderAccount, BigDecimal amount) {
        return senderAccount.getAvailableBalance().compareTo(amount) > 0;
    }

    private void addEntryToAccount(Account account, BigDecimal amount) {
        Entry entry = new Entry(amount);
        account.addEntry(entry);
    }
}

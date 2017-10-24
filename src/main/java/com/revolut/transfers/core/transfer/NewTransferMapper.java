package com.revolut.transfers.core.transfer;

import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.core.account.Account;
import com.revolut.transfers.core.account.AccountService;
import com.revolut.transfers.core.exception.AccountDoesNotExistException;

import java.math.BigDecimal;
import java.util.Optional;

public class NewTransferMapper {

    private AccountService accountService;

    public NewTransferMapper(AccountService accountService) {
        this.accountService = accountService;
    }

    public NewTransfer mapFrom(TransferRequest transferRequest) {
        Optional<Account> senderAccount = accountService.getAccount(transferRequest.getSender());
        Optional<Account> receiverAccount = accountService.getAccount(transferRequest.getReceiver());

        if (!senderAccount.isPresent() || !receiverAccount.isPresent()) {
            throw new AccountDoesNotExistException();
        }

        BigDecimal amount = new BigDecimal(transferRequest.getAmount());
        String description = transferRequest.getDescription();
        return new NewTransfer(senderAccount.get(), receiverAccount.get(), amount, description);
    }

}


package com.revolut.transfers.core.transfer;

import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.core.account.Account;
import com.revolut.transfers.core.account.AccountService;
import com.revolut.transfers.core.exception.AccountDoesNotExistException;
import com.revolut.transfers.core.exception.DoubleSpendingAttemptedException;

import java.math.BigDecimal;
import java.util.Optional;

public class NewTransferMapper {

    private AccountService accountService;
    private TransferService transferService;

    public NewTransferMapper(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    public NewTransfer mapFrom(TransferRequest transferRequest) {
        String nonce = transferRequest.getNonce();
        if (transferService.hasNonce(nonce)) {
            throw new DoubleSpendingAttemptedException();
        }

        Optional<Account> senderAccount = accountService.getAccount(transferRequest.getSender());
        Optional<Account> receiverAccount = accountService.getAccount(transferRequest.getReceiver());
        if (!senderAccount.isPresent() || !receiverAccount.isPresent()) {
            throw new AccountDoesNotExistException();
        }

        BigDecimal amount = transferRequest.getAmount();
        String description = transferRequest.getDescription();
        return new NewTransfer(nonce, senderAccount.get(), receiverAccount.get(), amount, description);
    }

}


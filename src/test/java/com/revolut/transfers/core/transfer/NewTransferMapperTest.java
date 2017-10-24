package com.revolut.transfers.core.transfer;

import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.core.account.Account;
import com.revolut.transfers.core.account.AccountService;
import com.revolut.transfers.core.exception.AccountDoesNotExistException;
import com.revolut.transfers.core.exception.DoubleSpendingAttemptedException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewTransferMapperTest {

    private static final AccountService accountService = mock(AccountService.class);
    private static final TransferService transferService = mock(TransferService.class);

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String NONCE = UUID.randomUUID().toString();
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";
    private static final String DESCRIPTION = "for lunch";
    private static final BigDecimal AMOUNT = new BigDecimal("200.00");
    private static final BigDecimal OVER_DRAFT_LIMIT_ZERO = new BigDecimal("0.00");

    @Test
    public void shouldMapRequestToNewTransfer() throws Exception {
        Account senderAccount = new Account(SENDER_ACCOUNT_ID, OVER_DRAFT_LIMIT_ZERO);
        Account receiverAccount = new Account(RECEIVER_ACCOUNT_ID, OVER_DRAFT_LIMIT_ZERO);

        when(accountService.getAccount(SENDER_ACCOUNT_ID)).thenReturn(Optional.of(senderAccount));
        when(accountService.getAccount(RECEIVER_ACCOUNT_ID)).thenReturn(Optional.of(receiverAccount));
        when(transferService.hasNonce(NONCE)).thenReturn(false);

        NewTransferMapper newTransferMapper = new NewTransferMapper(accountService, transferService);
        TransferRequest transferRequest = new TransferRequest(NONCE, SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, AMOUNT, DESCRIPTION);

        NewTransfer newTransfer = newTransferMapper.mapFrom(transferRequest);

        assertThat(newTransfer.getAmount(), is(AMOUNT));
        assertThat(newTransfer.getDescription(), is(DESCRIPTION));
        assertThat(newTransfer.getSenderAccount(), is(senderAccount));
        assertThat(newTransfer.getReceiverAccount(), is(receiverAccount));
    }

    @Test(expected = DoubleSpendingAttemptedException.class)
    public void shouldThrowDoubleSpendingAttemptExceptionWhenNonceExists() throws Exception {
        Account senderAccount = new Account(SENDER_ACCOUNT_ID, OVER_DRAFT_LIMIT_ZERO);
        Account receiverAccount = new Account(RECEIVER_ACCOUNT_ID, OVER_DRAFT_LIMIT_ZERO);

        when(accountService.getAccount(SENDER_ACCOUNT_ID)).thenReturn(Optional.of(senderAccount));
        when(accountService.getAccount(RECEIVER_ACCOUNT_ID)).thenReturn(Optional.of(receiverAccount));
        when(transferService.hasNonce(NONCE)).thenReturn(true);

        NewTransferMapper newTransferMapper = new NewTransferMapper(accountService, transferService);
        TransferRequest transferRequest = new TransferRequest(NONCE, SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, AMOUNT, DESCRIPTION);

        newTransferMapper.mapFrom(transferRequest);

    }

    @Test(expected = AccountDoesNotExistException.class)
    public void shouldThrowAccountNotFoundExceptionWhenAccountDoesNotExist() throws Exception {
        Account senderAccount = new Account(SENDER_ACCOUNT_ID, OVER_DRAFT_LIMIT_ZERO);

        when(transferService.hasNonce(NONCE)).thenReturn(false);
        when(accountService.getAccount(SENDER_ACCOUNT_ID)).thenReturn(Optional.of(senderAccount));
        when(accountService.getAccount(RECEIVER_ACCOUNT_ID)).thenReturn(Optional.empty());

        NewTransferMapper newTransferMapper = new NewTransferMapper(accountService, transferService);
        TransferRequest transferRequest = new TransferRequest(NONCE, SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, AMOUNT, DESCRIPTION);

        newTransferMapper.mapFrom(transferRequest);

    }
}
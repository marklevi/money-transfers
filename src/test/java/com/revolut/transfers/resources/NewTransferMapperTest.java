package com.revolut.transfers.resources;

import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.core.Account;
import com.revolut.transfers.core.AccountService;
import com.revolut.transfers.core.NewTransfer;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewTransferMapperTest {

    private static final AccountService accountService = mock(AccountService.class);

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";
    private static final String DESCRIPTION = "for lunch";
    private static final String AMOUNT = "200.00";

    @Test
    public void shouldMapRequestToNewTransfer() throws Exception {
        Account senderAccount = new Account(SENDER_ACCOUNT_ID, new BigDecimal("0.00"));
        Account receiverAccount = new Account(RECEIVER_ACCOUNT_ID, new BigDecimal("0.00"));

        when(accountService.getAccount(SENDER_ACCOUNT_ID)).thenReturn(Optional.of(senderAccount));
        when(accountService.getAccount(RECEIVER_ACCOUNT_ID)).thenReturn(Optional.of(receiverAccount));

        NewTransferMapper newTransferMapper = new NewTransferMapper(accountService);
        TransferRequest transferRequest = new TransferRequest(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, AMOUNT, DESCRIPTION);

        NewTransfer newTransfer = newTransferMapper.mapFrom(transferRequest);

        assertThat(newTransfer.getAmount(), is(new BigDecimal(AMOUNT)));
        assertThat(newTransfer.getDescription(), is(DESCRIPTION));
        assertThat(newTransfer.getSenderAccount(), is(senderAccount));
        assertThat(newTransfer.getReceiverAccount(), is(receiverAccount));
    }

    @Test(expected = AccountDoesNotExistException.class)
    public void shouldThrowAccountNotFoundExceptionWhenAccountDoesNotExist() throws Exception {
        Account senderAccount = new Account(SENDER_ACCOUNT_ID, new BigDecimal("0.00"));

        when(accountService.getAccount(SENDER_ACCOUNT_ID)).thenReturn(Optional.of(senderAccount));
        when(accountService.getAccount(RECEIVER_ACCOUNT_ID)).thenReturn(Optional.empty());

        NewTransferMapper newTransferMapper = new NewTransferMapper(accountService);
        TransferRequest transferRequest = new TransferRequest(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, AMOUNT, DESCRIPTION);

        newTransferMapper.mapFrom(transferRequest);

    }
}
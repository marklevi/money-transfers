package com.revolut.transfers.core;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TransferServiceTest {

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";
    private static final String FOR_LUNCH = "for lunch";

    private static final AccountService accountService = mock(AccountService.class);
    private TransferService transferService;

    @Before
    public void setUp() throws Exception {
        transferService = new TransferService(accountService);
    }

    @Test
    public void shouldUpdateBalanceOfRespectiveAccounts() throws Exception {
        BigDecimal AMOUNT = new BigDecimal("10.00");

        transferService.makeTransfer(new TransferDetails(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, AMOUNT, FOR_LUNCH));

        verify(accountService).updateBalance(SENDER_ACCOUNT_ID, AMOUNT.negate());
        verify(accountService).updateBalance(RECEIVER_ACCOUNT_ID, AMOUNT);

    }
}
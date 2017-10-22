package com.revolut.transfers.core;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferServiceTest {

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";
    private static final String FOR_LUNCH = "for lunch";

    private static final TransferRepo transferRepo = mock(TransferRepo.class);
    private TransferService transferService;
    private Account senderAccount;
    private Account receiverAccount ;

    @Before
    public void setUp() throws Exception {
        senderAccount = new Account(SENDER_ACCOUNT_ID);
        senderAccount.addEntry(new Entry(new BigDecimal("100.00"), LocalDate.now()));

        receiverAccount = new Account(RECEIVER_ACCOUNT_ID);
        receiverAccount.addEntry(new Entry(new BigDecimal("100.00"), LocalDate.now()));

        transferService = new TransferService(transferRepo);
    }

    @Test
    public void shouldUpdateBalanceOfRespectiveAccounts() throws Exception {
        BigDecimal ten = new BigDecimal("10.00");
        TransferDetails transferDetails = new TransferDetails(senderAccount, receiverAccount, ten, FOR_LUNCH);

        String expectedTransferId = UUID.randomUUID().toString();
        when(transferRepo.saveRecord(transferDetails)).thenReturn(expectedTransferId);

        String actualTransferId = transferService.makeTransfer(transferDetails);

        assertThat(actualTransferId, is(expectedTransferId));

        assertThat(senderAccount.getBalance(), is(new BigDecimal("90.00")));
        assertThat(receiverAccount.getBalance(), is(new BigDecimal("110.00")));


    }
}
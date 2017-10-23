package com.revolut.transfers.core;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferServiceTest {

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";
    private static final String FOR_LUNCH = "for lunch";

    private static final TransferRepo transferRepo = mock(TransferRepo.class);
    private TransferService transferService;

    @Before
    public void setUp() throws Exception {
        transferService = new TransferService(transferRepo);
    }

    @Test
    public void shouldUpdateBalanceOfRespectiveAccounts() throws Exception {
        Account senderAccount = new Account(SENDER_ACCOUNT_ID, new BigDecimal("0.00"));
        senderAccount.addEntry(new Entry(new BigDecimal("100.00"), LocalDate.now()));

        Account receiverAccount = new Account(RECEIVER_ACCOUNT_ID, new BigDecimal("0.00"));
        receiverAccount.addEntry(new Entry(new BigDecimal("100.00"), LocalDate.now()));

        NewTransfer newTransfer = new NewTransfer(senderAccount, receiverAccount, new BigDecimal("10.00"), FOR_LUNCH);

        String expectedTransferId = UUID.randomUUID().toString();
        when(transferRepo.addTransfer(newTransfer)).thenReturn(expectedTransferId);

        String actualTransferId = transferService.transfer(newTransfer);

        assertThat(actualTransferId, is(expectedTransferId));

        assertThat(senderAccount.getBalance(), is(new BigDecimal("90.00")));
        assertThat(receiverAccount.getBalance(), is(new BigDecimal("110.00")));
    }

    @Test
    public void shouldAllowTransferForCustomerOverdraft() throws Exception {
        Account senderAccount = new Account(SENDER_ACCOUNT_ID, new BigDecimal("100.00"));
        senderAccount.addEntry(new Entry(new BigDecimal("10.00"), LocalDate.now()));

        Account receiverAccount = new Account(RECEIVER_ACCOUNT_ID, new BigDecimal("0.00"));
        receiverAccount.addEntry(new Entry(new BigDecimal("100.00"), LocalDate.now()));

        NewTransfer newTransfer = new NewTransfer(senderAccount, receiverAccount, new BigDecimal("20.00"), FOR_LUNCH);

        String expectedTransferId = UUID.randomUUID().toString();
        when(transferRepo.addTransfer(newTransfer)).thenReturn(expectedTransferId);

        String actualTransferId = transferService.transfer(newTransfer);

        assertThat(actualTransferId, is(expectedTransferId));

        assertThat(senderAccount.getBalance(), is(new BigDecimal("10.00").negate()));
        assertThat(receiverAccount.getBalance(), is(new BigDecimal("120.00")));
    }

    @Test
    public void shouldDeclineTransferForPaymentThatGoesOverCustomerLimit() throws Exception {
        BigDecimal senderOverDraftLimit = new BigDecimal("10.00");
        Account senderAccount = new Account(SENDER_ACCOUNT_ID, senderOverDraftLimit);
        BigDecimal senderBalance = new BigDecimal("20.00");
        senderAccount.addEntry(new Entry(senderBalance, LocalDate.now()));

        BigDecimal receiverOverDraftLimit = new BigDecimal("0.00");
        Account receiverAccount = new Account(RECEIVER_ACCOUNT_ID, receiverOverDraftLimit);
        BigDecimal receiverBalance = new BigDecimal("100.00");
        receiverAccount.addEntry(new Entry(receiverBalance, LocalDate.now()));

        BigDecimal amount = new BigDecimal("40.00");
        NewTransfer newTransfer = new NewTransfer(senderAccount, receiverAccount, amount, FOR_LUNCH);
        try {
            transferService.transfer(newTransfer);
            fail("newTransfer service should throw insufficient funds exception");
        } catch (InsufficientFundsException ex) {
            assertThat(senderAccount.getBalance(), is(senderBalance));
            assertThat(receiverAccount.getBalance(), is(receiverBalance));
        }


    }
}
package com.revolut.transfers.core.transfer;

import com.revolut.transfers.core.account.Account;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TransferRepoTest {

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";
    private static final String FOR_LUNCH = "for lunch";
    private static final String NONCE = UUID.randomUUID().toString();

    private Account senderAccount;
    private Account receiverAccount;

    @Before
    public void setUp() throws Exception {
        senderAccount = new Account(SENDER_ACCOUNT_ID, new BigDecimal("0.00"));
        receiverAccount = new Account(RECEIVER_ACCOUNT_ID, new BigDecimal("0.00"));
    }

    @Test
    public void addTransferDetailsToRepo() throws Exception {
        NewTransfer newTransfer = new NewTransfer(NONCE, senderAccount, receiverAccount, new BigDecimal("10.00"), FOR_LUNCH);

        TransferRepo transferRepo = new TransferRepo();
        Transfer transfer = transferRepo.addTransfer(new Transfer(newTransfer));

        assertThat(transferRepo.getTransfer(transfer.getId()), is(Optional.of(transfer)));


    }

    @Test
    public void shouldReturnTrueIfRepoHasNonce() throws Exception {
        NewTransfer newTransfer = new NewTransfer(NONCE, senderAccount, receiverAccount, new BigDecimal("10.00"), FOR_LUNCH);

        TransferRepo transferRepo = new TransferRepo();
        transferRepo.addTransfer(new Transfer(newTransfer));

        assertTrue(transferRepo.hasNonce(NONCE));

    }

    @Test
    public void shouldReturnFalseIfRepoDoesNotHaveNonce() throws Exception {
        NewTransfer newTransfer = new NewTransfer(NONCE, senderAccount, receiverAccount, new BigDecimal("10.00"), FOR_LUNCH);

        TransferRepo transferRepo = new TransferRepo();
        transferRepo.addTransfer(new Transfer(newTransfer));

        assertFalse(transferRepo.hasNonce(UUID.randomUUID().toString()));

    }
}
package com.revolut.transfers.core;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class TransferRepoTest {

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";
    private static final String FOR_LUNCH = "for lunch";

    private Account senderAccount;
    private Account receiverAccount;

    @Before
    public void setUp() throws Exception {
        senderAccount = new Account(SENDER_ACCOUNT_ID);
        receiverAccount = new Account(RECEIVER_ACCOUNT_ID);
    }

    @Test
    public void addTransferDetailsToRepo() throws Exception {
        TransferRepo transferRepo = new TransferRepo();
        TransferDetails transferDetails = new TransferDetails(senderAccount, receiverAccount, new BigDecimal("10.00"), FOR_LUNCH);
        String transferId = transferRepo.addTransferDetails(transferDetails);

        assertThat(transferRepo.getTransferDetails(transferId), is(transferDetails));


    }
}
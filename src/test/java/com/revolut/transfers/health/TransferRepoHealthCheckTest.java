package com.revolut.transfers.health;

import com.revolut.transfers.core.transfer.TransferRepo;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TransferRepoHealthCheckTest {

    private TransferRepo transferRepo = mock(TransferRepo.class);
    private TransferRepoHealthCheck transferRepoHealthCheck = new TransferRepoHealthCheck(transferRepo);

    @Test
    public void shouldReturnHealthyIfConnected() throws Exception {
        when(transferRepo.isConnected()).thenReturn(true);

        assertTrue(transferRepoHealthCheck.check().isHealthy());
    }

    @Test
    public void shouldReturnUnhealthyIfNotConnected() throws Exception {
        when(transferRepo.isConnected()).thenReturn(false);

        assertFalse(transferRepoHealthCheck.check().isHealthy());
    }

}
package com.revolut.transfers.health;

import com.codahale.metrics.health.HealthCheck;
import com.revolut.transfers.core.transfer.TransferRepo;


public class TransferRepoHealthCheck extends HealthCheck {

    private final TransferRepo repo;

    public TransferRepoHealthCheck(TransferRepo transferRepo) {
        this.repo = transferRepo;
    }

    @Override
    protected Result check() throws Exception {
        if (repo.isConnected()) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Cannot connect to the database");
        }
    }
}
package com.revolut.transfers.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransferRepo {

    private final Map<String, Transfer> dataStore;

    public TransferRepo() {
        dataStore = new HashMap<>();
    }

    public String addTransfer(Transfer transfer) {
        String transferId = UUID.randomUUID().toString();
        dataStore.put(transferId, transfer);
        return transferId;
    }

    public Transfer getTransfer(String transferId) {
        return dataStore.get(transferId);
    }
}

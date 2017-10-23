package com.revolut.transfers.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransferRepo {

    private final Map<String, NewTransfer> dataStore;

    public TransferRepo() {
        dataStore = new HashMap<>();
    }

    public String addTransfer(NewTransfer newTransfer) {
        String transferId = UUID.randomUUID().toString();
        dataStore.put(transferId, newTransfer);
        return transferId;
    }

    public NewTransfer getTransfer(String transferId) {
        return dataStore.get(transferId);
    }
}

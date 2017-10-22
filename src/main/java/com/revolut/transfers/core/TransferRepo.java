package com.revolut.transfers.core;

import java.util.HashMap;
import java.util.UUID;

public class TransferRepo {

    private final HashMap<String, TransferDetails> dataStore;

    public TransferRepo() {
        dataStore = new HashMap<>();
    }

    public String addTransferDetails(TransferDetails transferDetails) {
        String transferId = UUID.randomUUID().toString();
        dataStore.put(transferId, transferDetails);
        return transferId;
    }

    public TransferDetails getTransferDetails(String transferId) {
        return dataStore.get(transferId);
    }
}

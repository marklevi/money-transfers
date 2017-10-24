package com.revolut.transfers.core;

import java.util.HashMap;
import java.util.Map;

public class TransferRepo {

    private final Map<String, Transfer> dataStore;

    public TransferRepo() {
        dataStore = new HashMap<>();
    }

    public Transfer addTransfer(Transfer transfer) {
        dataStore.put(transfer.getId(), transfer);
        return transfer;
    }

    public Transfer getTransfer(String transferId) {
        return dataStore.get(transferId);
    }
}

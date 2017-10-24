package com.revolut.transfers.core.transfer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class TransferRepo {

    private final Map<String, Transfer> dataStore;

    public TransferRepo() {
        dataStore = new HashMap<>();
    }

    public Transfer addTransfer(Transfer transfer) {
        dataStore.put(transfer.getId(), transfer);
        return transfer;
    }

    public Optional<Transfer> getTransfer(String transferId) {
        return Optional.ofNullable(dataStore.get(transferId));
    }

    public boolean hasNonce(String nonce) {
        return dataStore.values().stream().anyMatch(t -> Objects.equals(t.getNonce(), nonce));
    }
}

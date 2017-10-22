package com.revolut.transfers.core;

import java.util.UUID;

public class TransferRepo {
    public String saveRecord(TransferDetails transferDetails) {
        return UUID.randomUUID().toString();
    }
}

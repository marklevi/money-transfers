package com.revolut.transfers.core;

public class TransferMapper {
    public Transfer mapFrom(NewTransfer newTransfer) {
        return new Transfer(newTransfer);
    }
}

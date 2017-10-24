package com.revolut.transfers.core.transfer;

public class TransferMapper {
    public Transfer mapFrom(NewTransfer newTransfer) {
        return new Transfer(newTransfer);
    }
}

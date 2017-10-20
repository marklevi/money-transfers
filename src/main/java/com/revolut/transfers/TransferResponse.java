package com.revolut.transfers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferResponse {

    @JsonProperty
    private String transferId;

    public TransferResponse(String transferId) {
        this.transferId = transferId;
    }

    public String getTransferId() {
        return transferId;
    }
}

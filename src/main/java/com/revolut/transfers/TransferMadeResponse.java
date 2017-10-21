package com.revolut.transfers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferMadeResponse extends StructuralEquivalence {

    @JsonProperty
    private String transferId;

    public TransferMadeResponse() {
        /* For Jackson */
    }

    public TransferMadeResponse(String transferId) {
        this.transferId = transferId;
    }

    public String getTransferId() {
        return transferId;
    }
}

package com.revolut.transfers.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revolut.transfers.utils.StructuralEquivalence;

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

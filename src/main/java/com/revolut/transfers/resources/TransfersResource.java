package com.revolut.transfers.resources;

import com.revolut.transfers.api.TransferMadeResponse;
import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.core.NewTransfer;
import com.revolut.transfers.core.TransferService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/transfers")
@Produces(MediaType.APPLICATION_JSON)
public class TransfersResource {

    private TransferService transferService;
    private NewTransferMapper newTransferMapper;

    public TransfersResource(TransferService transferService, NewTransferMapper newTransferMapper) {
        this.transferService = transferService;
        this.newTransferMapper = newTransferMapper;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response makeTransfer(@Valid TransferRequest transferRequest) {
        NewTransfer newTransfer = newTransferMapper.mapFrom(transferRequest);

        String transferId = transferService.transfer(newTransfer);
        TransferMadeResponse transferMadeResponse = new TransferMadeResponse(transferId);
        return Response.status(Response.Status.CREATED).entity(transferMadeResponse).build();

    }

}

package com.revolut.transfers.resources;

import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.api.TransferResponse;
import com.revolut.transfers.core.transfer.NewTransfer;
import com.revolut.transfers.core.transfer.NewTransferMapper;
import com.revolut.transfers.core.transfer.Transfer;
import com.revolut.transfers.core.transfer.TransferService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Optional;

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

    @GET
    @Path("/{id}")
    public Response getTransfer(@PathParam("id") String id){
        Optional<Transfer> transfer = transferService.getTransfer(id);
        if (transfer.isPresent()) {
            TransferResponse entity = new TransferResponse(transfer.get());
            return Response.status(Response.Status.OK).entity(entity).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response makeTransfer(@Valid TransferRequest transferRequest) {
        NewTransfer newTransfer = newTransferMapper.mapFrom(transferRequest);
        Transfer transfer = transferService.transfer(newTransfer);
        return Response.seeOther(getRedirectUri(transfer.getId())).build();

    }

    private URI getRedirectUri(String transferId) {
        return UriBuilder.fromResource(TransfersResource.class).path(transferId).build();
    }

}

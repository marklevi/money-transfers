package com.revolut.transfers.resources;

import com.revolut.transfers.api.TransferDetailsRequest;
import com.revolut.transfers.api.TransferMadeResponse;
import com.revolut.transfers.core.AccountService;
import com.revolut.transfers.core.TransferDetails;
import com.revolut.transfers.core.TransferService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/transfers")
@Produces(MediaType.APPLICATION_JSON)
public class TransfersResource {

    private AccountService accountService;
    private TransferService transferService;

    public TransfersResource(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response makeTransfer(@Valid TransferDetailsRequest transferDetailsRequest) {
        String receiverAccountId = transferDetailsRequest.getReceiver();
        String senderAccountId = transferDetailsRequest.getSender();
        List<String> accountIds = Arrays.asList(senderAccountId, receiverAccountId);

        if (!accountService.hasAccounts(accountIds)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String transferId = transferService.makeTransfer(getTransferDetails(transferDetailsRequest));
        TransferMadeResponse transferMadeResponse = new TransferMadeResponse(transferId);
        return Response.status(Response.Status.CREATED).entity(transferMadeResponse).build();

    }

    private TransferDetails getTransferDetails(TransferDetailsRequest transferDetailsRequest) {
        return new TransferDetails(transferDetailsRequest.getSender(), transferDetailsRequest.getReceiver(), new BigDecimal(transferDetailsRequest.getAmount()), transferDetailsRequest.getDescription());
    }
}

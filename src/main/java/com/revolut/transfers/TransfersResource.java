package com.revolut.transfers;

import com.google.common.collect.ImmutableMap;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/transfers")
@Produces(MediaType.APPLICATION_JSON)
public class TransfersResource {

    private AccountService accountService;

    public TransfersResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    public Map<String, String> sayHello() {
        return ImmutableMap.of("hello", "world!");
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response makeTransfer(@Valid TransferDetailsRequest transferDetailsRequest) {
        String receiverAccountId = transferDetailsRequest.getReceiver();
        String senderAccountId = transferDetailsRequest.getSender();
        List<String> accountIds = Arrays.asList(receiverAccountId, senderAccountId);

        if (!accountService.accountsExist(accountIds)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        TransferMadeResponse transferMadeResponse = new TransferMadeResponse(UUID.randomUUID().toString());
        return Response.status(Response.Status.CREATED).entity(transferMadeResponse).build();

    }
}

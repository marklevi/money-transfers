package com.revolut.transfers;

import com.google.common.collect.ImmutableMap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/transfers")
@Produces(MediaType.APPLICATION_JSON)
public class TransfersResource {

    public TransfersResource() {
    }

    @GET
    public Map<String, String> sayHello() {
        return ImmutableMap.of("hello", "world!");
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response makeTransfer() {
        TransferResponse transferResponse = new TransferResponse(UUID.randomUUID().toString());
        return Response.status(Response.Status.CREATED).entity(transferResponse).build();

    }
}

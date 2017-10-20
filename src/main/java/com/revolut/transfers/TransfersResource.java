package com.revolut.transfers;

import com.google.common.collect.ImmutableMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/transfers")
@Produces(MediaType.APPLICATION_JSON)
public class TransfersResource {

    public TransfersResource() {
    }

    @GET
    public Map<String, String> sayHello() {
        return ImmutableMap.of("hello", "world!");
    }
}

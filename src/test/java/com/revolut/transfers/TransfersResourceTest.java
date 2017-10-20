package com.revolut.transfers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Map;

import static com.revolut.transfers.MoneyTransfersApplication.decorateObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;

public class TransfersResourceTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .setMapper(decorateObjectMapper(new ObjectMapper()))
            .addResource(new TransfersResource())
            .build();

    @Test
    public void getHelloWorld() {
        Map<String, String> expectedResponse = ImmutableMap.of("hello", "world!");

        Map<String, String> actualResponse = resources.client().target("/transfers").request().get(Map.class);
        assertThat(actualResponse)
                .isEqualTo(expectedResponse);
    }

    @Test
    public void makeTransfer() {
        TransferDetailsRequest transferDetailsRequest = new TransferDetailsRequest("sender", "receiver", "amount", "description");

        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferDetailsRequest));

        assertThat(response.getStatus())
                .isEqualTo(CREATED_201);
    }
}


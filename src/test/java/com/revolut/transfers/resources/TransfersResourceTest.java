package com.revolut.transfers.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.revolut.transfers.api.TransferDetailsRequest;
import com.revolut.transfers.api.TransferMadeResponse;
import com.revolut.transfers.core.AccountService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.revolut.transfers.MoneyTransfersApplication.decorateObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransfersResourceTest {

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";

    private static final AccountService accountService = mock(AccountService.class);


    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .setMapper(decorateObjectMapper(new ObjectMapper()))
            .addResource(new TransfersResource(accountService))
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
        when(accountService.accountsExist(Arrays.asList(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID))).thenReturn(true);

        TransferDetailsRequest transferDetailsRequest = new TransferDetailsRequest(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, "200.00", "description");

        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferDetailsRequest));

        assertThat(response.getStatus())
                .isEqualTo(CREATED_201);

        TransferMadeResponse transferMadeResponse = response.readEntity(TransferMadeResponse.class);
        assertThat(transferMadeResponse)
                .isInstanceOfAny(TransferMadeResponse.class);
    }

    @Test
    public void shouldReturn404WhenAccountDoesNotExist() {
        when(accountService.accountsExist(Arrays.asList(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID))).thenReturn(false);

        TransferDetailsRequest transferDetailsRequest = new TransferDetailsRequest(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, "200.00", "description");
        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferDetailsRequest));

        assertThat(response.getStatus())
                .isEqualTo(NOT_FOUND_404);

    }

    @Test
    public void shouldReturn422WhenAnyRequestsFieldsExceptDescriptionAreBlank() {
        TransferDetailsRequest transferDetailsRequest = new TransferDetailsRequest("", RECEIVER_ACCOUNT_ID, "", "description");
        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferDetailsRequest));

        assertThat(response.getStatus())
                .isEqualTo(UNPROCESSABLE_ENTITY_422);

    }



}

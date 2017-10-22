package com.revolut.transfers.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.transfers.api.TransferDetailsRequest;
import com.revolut.transfers.api.TransferMadeResponse;
import com.revolut.transfers.core.Account;
import com.revolut.transfers.core.AccountService;
import com.revolut.transfers.core.TransferDetails;
import com.revolut.transfers.core.TransferService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static com.revolut.transfers.MoneyTransfersApplication.decorateObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransfersResourceTest {

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";

    private static final AccountService accountService = mock(AccountService.class);
    private static final TransferService transferService = mock(TransferService.class);


    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .setMapper(decorateObjectMapper(new ObjectMapper()))
            .addResource(new TransfersResource(accountService, transferService))
            .build();
    private static final String AMOUNT = "200.00";
    private static final String DESCRIPTION = "description";

    @Test
    public void makeTransfer() {
        String transferId = UUID.randomUUID().toString();
        when(accountService.hasAccounts(Arrays.asList(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID))).thenReturn(true);

        Account senderAccount = createAccount(SENDER_ACCOUNT_ID);
        when(accountService.getAccount(SENDER_ACCOUNT_ID)).thenReturn(senderAccount);

        Account receiverAccount = createAccount(RECEIVER_ACCOUNT_ID);
        when(accountService.getAccount(RECEIVER_ACCOUNT_ID)).thenReturn(receiverAccount);

        when(transferService.makeTransfer(new TransferDetails(senderAccount, receiverAccount, new BigDecimal(AMOUNT), DESCRIPTION))).thenReturn(transferId);

        TransferDetailsRequest transferDetailsRequest = new TransferDetailsRequest(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, AMOUNT, DESCRIPTION);
        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferDetailsRequest));

        assertThat(response.getStatus())
                .isEqualTo(CREATED_201);

        TransferMadeResponse transferMadeResponse = response.readEntity(TransferMadeResponse.class);
        assertThat(transferMadeResponse.getTransferId()).isEqualTo(transferId);
    }

    private Account createAccount(String senderAccountId) {
        return new Account(senderAccountId);
    }

    @Test
    public void shouldReturn404WhenAccountDoesNotExist() {
        when(accountService.hasAccounts(Arrays.asList(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID))).thenReturn(false);

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

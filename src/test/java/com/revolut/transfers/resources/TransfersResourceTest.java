package com.revolut.transfers.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.api.TransferMadeResponse;
import com.revolut.transfers.core.Account;
import com.revolut.transfers.core.AccountService;
import com.revolut.transfers.core.Transfer;
import com.revolut.transfers.core.TransferService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

import static com.revolut.transfers.MoneyTransfersApplication.decorateObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransfersResourceTest {

    private static final String SENDER_ACCOUNT_ID = "sender-account-id";
    private static final String RECEIVER_ACCOUNT_ID = "receiver-account-id";

    private static final TransferService transferService = mock(TransferService.class);
    private static final NewTransferMapper newTransferMapper = mock(NewTransferMapper.class);


    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .setMapper(decorateObjectMapper(new ObjectMapper()))
            .addResource(new TransfersResource(transferService, newTransferMapper))
            .build();
    private static final String AMOUNT = "200.00";
    private static final String DESCRIPTION = "description";
    private static final String INVALID_ACCOUNT_ID = "invalid-account-id";


    @Test
    public void makeTransfer() {
        String transferId = UUID.randomUUID().toString();

        Account senderAccount = createAccount(SENDER_ACCOUNT_ID);
        Account receiverAccount = createAccount(RECEIVER_ACCOUNT_ID);

        TransferRequest transferRequest = new TransferRequest(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, AMOUNT, DESCRIPTION);
        Transfer newTransfer = new Transfer(senderAccount, receiverAccount, new BigDecimal(AMOUNT), DESCRIPTION);

        when(newTransferMapper.mapFrom(transferRequest)).thenReturn(newTransfer);
        when(transferService.transfer(newTransfer)).thenReturn(transferId);

        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferRequest));

        assertThat(response.getStatus())
                .isEqualTo(CREATED_201);

        TransferMadeResponse transferMadeResponse = response.readEntity(TransferMadeResponse.class);
        assertThat(transferMadeResponse.getTransferId()).isEqualTo(transferId);
    }

    private Account createAccount(String senderAccountId) {
        return new Account(senderAccountId, new BigDecimal("0.00"));
    }

    // TODO: 23/10/2017 return 422 response code with exception mapper
    @Test()
    public void shouldReturn422WhenEitherAccountDoesNotExist() {
        TransferRequest transferRequest = new TransferRequest(SENDER_ACCOUNT_ID, INVALID_ACCOUNT_ID, "200.00", "description");
        when(newTransferMapper.mapFrom(transferRequest)).thenThrow(AccountDoesNotExistException.class);

        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferRequest));

        assertThat(response.getStatus())
                .isEqualTo(INTERNAL_SERVER_ERROR_500);

    }

    @Test
    public void shouldReturn422WhenAnyRequestsFieldsExceptDescriptionAreBlank() {
        TransferRequest transferRequest = new TransferRequest("", RECEIVER_ACCOUNT_ID, "", "description");
        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferRequest));

        assertThat(response.getStatus())
                .isEqualTo(UNPROCESSABLE_ENTITY_422);

    }
}

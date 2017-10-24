package com.revolut.transfers.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.api.TransferResponse;
import com.revolut.transfers.core.account.Account;
import com.revolut.transfers.core.exception.AccountDoesNotExistException;
import com.revolut.transfers.core.transfer.NewTransfer;
import com.revolut.transfers.core.transfer.NewTransferMapper;
import com.revolut.transfers.core.transfer.Transfer;
import com.revolut.transfers.core.transfer.TransferService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Optional;

import static com.revolut.transfers.MoneyTransfersApplication.decorateObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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
    private static final BigDecimal AMOUNT = new BigDecimal("200.00");
    private static final String DESCRIPTION = "description";
    private static final String INVALID_ACCOUNT_ID = "invalid-account-id";


    @Test
    public void makeTransfer() {
        Account senderAccount = createAccount(SENDER_ACCOUNT_ID);
        Account receiverAccount = createAccount(RECEIVER_ACCOUNT_ID);

        TransferRequest transferRequest = new TransferRequest(SENDER_ACCOUNT_ID, RECEIVER_ACCOUNT_ID, AMOUNT, DESCRIPTION);
        NewTransfer newTransfer = new NewTransfer(senderAccount, receiverAccount, AMOUNT, DESCRIPTION);

        when(newTransferMapper.mapFrom(transferRequest)).thenReturn(newTransfer);
        Transfer expectedTransfer = new Transfer(newTransfer);
        when(transferService.transfer(newTransfer)).thenReturn(expectedTransfer);
        when(transferService.getTransfer(expectedTransfer.getId())).thenReturn(Optional.of(expectedTransfer));

        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferRequest));

        assertThat(response.getStatus())
                .isEqualTo(OK_200);

        TransferResponse transferResponse = response.readEntity(TransferResponse.class);
        assertThat(transferResponse.getId(), is(expectedTransfer.getId()));
    }

    private Account createAccount(String senderAccountId) {
        return new Account(senderAccountId, new BigDecimal("0.00"));
    }

    // TODO: 23/10/2017 return 422 response code with exception mapper
    @Test()
    public void shouldReturn422WhenEitherAccountDoesNotExist() {
        TransferRequest transferRequest = new TransferRequest(SENDER_ACCOUNT_ID, INVALID_ACCOUNT_ID, AMOUNT, "description");
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
        TransferRequest transferRequest = new TransferRequest("", RECEIVER_ACCOUNT_ID, AMOUNT, "description");
        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferRequest));

        assertThat(response.getStatus())
                .isEqualTo(UNPROCESSABLE_ENTITY_422);

    }

    @Test
    public void shouldReturn422WhenAnyAmountIsInvalid() {
        TransferRequest transferRequest = new TransferRequest("", RECEIVER_ACCOUNT_ID, AMOUNT.negate(), "description");
        Response response = resources.client()
                .target("/transfers")
                .request()
                .post(Entity.json(transferRequest));

        assertThat(response.getStatus())
                .isEqualTo(UNPROCESSABLE_ENTITY_422);
    }
}

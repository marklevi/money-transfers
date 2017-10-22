package com.revolut.transfers.resources;

import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.api.TransferMadeResponse;
import com.revolut.transfers.core.Account;
import com.revolut.transfers.core.AccountService;
import com.revolut.transfers.core.Transfer;
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
    public Response makeTransfer(@Valid TransferRequest transferRequest) {
        List<String> accountIds = getAccountIds(transferRequest);

        if (!accountService.hasAccounts(accountIds)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String transferId = transferService.preformTransfer(getTransfer(transferRequest));
        TransferMadeResponse transferMadeResponse = new TransferMadeResponse(transferId);
        return Response.status(Response.Status.CREATED).entity(transferMadeResponse).build();

    }

    private List<String> getAccountIds(@Valid TransferRequest transferRequest) {
        String receiverAccountId = transferRequest.getReceiver();
        String senderAccountId = transferRequest.getSender();
        return Arrays.asList(senderAccountId, receiverAccountId);
    }

    private Transfer getTransfer(TransferRequest transferRequest) {
        Account senderAccount = accountService.getAccount(transferRequest.getSender());
        Account receiverAccount = accountService.getAccount(transferRequest.getReceiver());
        BigDecimal amount = new BigDecimal(transferRequest.getAmount());
        String description = transferRequest.getDescription();

        return new Transfer(senderAccount, receiverAccount, amount, description);
    }
}

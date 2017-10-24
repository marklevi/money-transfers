package com.revolut.transfers.resources;

import com.revolut.transfers.core.account.Account;
import com.revolut.transfers.core.account.AccountRepo;
import com.revolut.transfers.core.account.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/seed-data")
public class SeedDataResource {
    private static final String SENDER_ACCOUNT_A = "sender-account-a";
    private static final String RECEIVER_ACCOUNT_B = "receiver-account-b";

    private AccountRepo accountRepo;

    public SeedDataResource(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @GET
    public Response seed() {
        Account accountA = accountRepo.addAccount(new Account(SENDER_ACCOUNT_A, new BigDecimal("0.00")));
        accountA.addEntry(new Entry(new BigDecimal("100")));

        Account accountB = accountRepo.addAccount(new Account(RECEIVER_ACCOUNT_B, new BigDecimal("0.00")));
        accountB.addEntry(new Entry(new BigDecimal("100")));

        return Response.status(Response.Status.OK).build();

    }
}

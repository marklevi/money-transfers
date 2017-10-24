package com.revolut.transfers.resources;

import com.revolut.transfers.MoneyTransfersApplication;
import com.revolut.transfers.MoneyTransfersConfiguration;
import com.revolut.transfers.api.TransferRequest;
import com.revolut.transfers.api.TransferResponse;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jetty.http.HttpStatus.OK_200;

public class TransferResourceAcceptanceTest {

    private static final String SOME_ACCOUNT_A = "sender-account-a";
    private static final String SOME_ACCOUNT_B = "receiver-account-b";
    private static final BigDecimal AMOUNT = new BigDecimal("10.00");
    private static final String DESCRIPTION = "description";

    private static final DropwizardTestSupport<MoneyTransfersConfiguration> SUPPORT =
            new DropwizardTestSupport<>(MoneyTransfersApplication.class,
                    ResourceHelpers.resourceFilePath("money-transfers-test.yml"));
    private Client client;

    @BeforeClass
    public static void beforeClass() {
        SUPPORT.before();
    }

    @AfterClass
    public static void afterClass() {
        SUPPORT.after();
    }

    @Before
    public void setUp() throws Exception {
        client = new JerseyClientBuilder().build();
        Response seedResponse = seedData();
        assertThat(seedResponse.getStatus()).isEqualTo(OK_200);

    }

    @Test
    public void CreateTransferRedirectsAfterPost() {
        Response response = client.target(
                String.format("http://localhost:%d/transfers", SUPPORT.getLocalPort()))
                .request()
                .post(Entity.json(transferRequest()));

        assertThat(response.getStatus()).isEqualTo(200);
        TransferResponse transferResponse = response.readEntity(TransferResponse.class);
        assertThat(transferResponse.getSender()).isEqualTo(SOME_ACCOUNT_A);
        assertThat(transferResponse.getReceiver()).isEqualTo(SOME_ACCOUNT_B);
        assertThat(transferResponse.getAmount()).isEqualTo(AMOUNT);
        assertThat(transferResponse.getDescription()).isEqualTo(DESCRIPTION);
    }

    private TransferRequest transferRequest() {
        return new TransferRequest(SOME_ACCOUNT_A, SOME_ACCOUNT_B, AMOUNT, DESCRIPTION);
    }

    private Response seedData() {
        return client.target(
                String.format("http://localhost:%d/seed-data", SUPPORT.getLocalPort()))
                .request()
                .get();
    }
}

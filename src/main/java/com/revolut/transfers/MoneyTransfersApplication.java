package com.revolut.transfers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.BigDecimalDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.revolut.transfers.core.account.AccountRepo;
import com.revolut.transfers.core.account.AccountService;
import com.revolut.transfers.core.transfer.NewTransferMapper;
import com.revolut.transfers.core.transfer.TransferMapper;
import com.revolut.transfers.core.transfer.TransferRepo;
import com.revolut.transfers.core.transfer.TransferService;
import com.revolut.transfers.resources.SeedDataResource;
import com.revolut.transfers.resources.TransferResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.math.BigDecimal;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class MoneyTransfersApplication extends Application<MoneyTransfersConfiguration> {


    public static ObjectMapper decorateObjectMapper(ObjectMapper objectMapper) {
        return objectMapper
                .registerModule(new Jdk8Module())
                .registerModule(new SimpleModule().addDeserializer(BigDecimal.class, new BigDecimalDeserializer()))
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static void main(String[] args) throws Exception {
        new MoneyTransfersApplication().run(args);
    }

    @Override
    public String getName() {
        return "money-transfers";
    }

    @Override
    public void initialize(Bootstrap<MoneyTransfersConfiguration> bootstrap) {
        decorateObjectMapper(bootstrap.getObjectMapper());
    }

    @Override
    public void run(MoneyTransfersConfiguration configuration, Environment environment) {
        AccountRepo accountRepo = new AccountRepo();
        environment.jersey().register(getTransferResource(accountRepo));
        environment.jersey().register(new SeedDataResource(accountRepo));
    }

    private TransferResource getTransferResource(AccountRepo accountRepo) {
        TransferService transferService = new TransferService(new TransferRepo(), new TransferMapper());
        AccountService accountService = new AccountService(accountRepo);
        NewTransferMapper newTransferMapper = new NewTransferMapper(accountService);

        return new TransferResource(transferService, newTransferMapper);
    }

}
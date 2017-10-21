package com.revolut.transfers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class MoneyTransfersApplication extends Application<MoneyTransfersConfiguration> {

    public static ObjectMapper decorateObjectMapper(ObjectMapper objectMapper) {
        return objectMapper
                .registerModule(new Jdk8Module())
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
        TransfersResource transfersResource = new TransfersResource(new AccountService(new AccountRepo()));
        environment.jersey().register(transfersResource );
    }

}
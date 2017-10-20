package com.revolut.transfers;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneyTransfersApplication extends Application<MoneyTransfersConfiguration> {
    public static void main(String[] args) throws Exception {
        new MoneyTransfersApplication().run(args);
    }

    @Override
    public String getName() {
        return "money-transfers";
    }

    @Override
    public void initialize(Bootstrap<MoneyTransfersConfiguration> bootstrap) {
    }

    @Override
    public void run(MoneyTransfersConfiguration configuration, Environment environment) {
        TransfersResource transfersResource = new TransfersResource();
        environment.jersey().register(transfersResource );
    }

}
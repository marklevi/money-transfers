package com.revolut.transfers.core.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static org.eclipse.jetty.http.HttpStatus.UNPROCESSABLE_ENTITY_422;

public class DoubleSpendingAttemptedExceptionMapper implements ExceptionMapper<DoubleSpendingAttemptedException> {

    @Override
    public Response toResponse(DoubleSpendingAttemptedException exception) {
        return Response.status(UNPROCESSABLE_ENTITY_422).build();
    }
}
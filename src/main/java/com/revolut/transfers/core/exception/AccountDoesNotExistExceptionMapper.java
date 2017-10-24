package com.revolut.transfers.core.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static org.eclipse.jetty.http.HttpStatus.UNPROCESSABLE_ENTITY_422;

public class AccountDoesNotExistExceptionMapper implements ExceptionMapper<AccountDoesNotExistException> {

    @Override
    public Response toResponse(AccountDoesNotExistException exception) {
        return Response.status(UNPROCESSABLE_ENTITY_422).build();
    }
}
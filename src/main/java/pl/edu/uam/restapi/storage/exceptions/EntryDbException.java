package pl.edu.uam.restapi.storage.exceptions;

import pl.edu.uam.restapi.storage.model.ErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class EntryDbException extends WebApplicationException {
    public EntryDbException(String message, String userMessage) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(createError(message, userMessage)).build());
    }

    private static ErrorMessage createError(String message, String userMessage) {
        return new ErrorMessage(message, userMessage);
    }
}

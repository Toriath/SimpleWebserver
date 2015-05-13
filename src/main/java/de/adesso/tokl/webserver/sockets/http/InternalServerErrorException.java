package de.adesso.tokl.webserver.sockets.http;

/**
 * Created by kloss on 13.05.2015.
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}

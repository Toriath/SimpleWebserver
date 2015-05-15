package de.adesso.tokl.webserver.sockets.http;

/**
 * Created by kloss on 13.05.2015.
 * This Exception allows to send the Http 500 Error in case the Server throws an exception.
 */
public class InternalServerErrorException extends RuntimeException {

    /**
     * Creates an Exception to allow to send the 500 Error
     * @param message the Message of the error
     * @param cause the Exception which caused this to get thrown
     */
    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}

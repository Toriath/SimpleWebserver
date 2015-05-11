package de.adesso.tokl.webserver.sockets;

/**
 * Created by kloss on 05.05.2015.
 *
 * Represents the most important errorpages for the HTTProtocol
 */
public enum HttpError {



    ERROR_404("HTTP/1.1 404 File Not Found\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: 27\r\n" +
            "\r\n" +
            "<h1>404 File Not Found</h1>"),


    ERROR_500("HTTP/1.1 500 Internal Server Error" +
            "Content-Type: text/html\r\n" +
            "Content-Length: 34\r\n" +
            "\r\n" +
            "<h1>500 Internal Server Error</h1>");



    private final String errorMessage;

    /**
     * Constructor to provide values for the enum
     * @param errorMessage the error Message
     */
    HttpError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }

    public byte[] getBytes() {
        return errorMessage.getBytes();
    }
}

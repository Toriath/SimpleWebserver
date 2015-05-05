package de.adesso.tokl.webserver.sockets;

/**
 * Created by kloss on 05.05.2015.
 *
 * Represents the most important errorpages for the HTTPprotocol
 */
public enum HttpError {


    ERROR_404("HTTP/1.1 404 File Not Found\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: 23\r\n" +
            "\r\n" +
            "<h1>File Not Found</h1>");

    private String errorMessage;

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

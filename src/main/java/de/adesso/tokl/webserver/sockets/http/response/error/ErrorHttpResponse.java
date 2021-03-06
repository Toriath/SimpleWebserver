package de.adesso.tokl.webserver.sockets.http.response.error;

import de.adesso.tokl.webserver.sockets.http.response.HttpResponse;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 * Represents an http response sending an error
 */
public final  class ErrorHttpResponse extends HttpResponse {

    private final String errorMessage;
    private final String statusCode;
    private final String header;

    /**
     * Construct a HttpError
     * @param socket the socket to respond
     * @param errorCode The error code in the format "000 message..."
     * @param errorMessage The Message shown to the user
     */
    private ErrorHttpResponse(Socket socket, String errorCode, String errorMessage) {
        super(socket);
        this.errorMessage = errorMessage;
        this.statusCode = "HTTP/1.1 " + errorCode + "\r\n";
        this.header = "Content-Type: text/html\r\n" + "Content-Length: " + errorMessage.length() + "\r\n\r\n";

    }

    /**
     * Creates an Instance of this class representing a 404 Error
     * @param socket the socket this Error will be send to
     * @return the ErrorHttpResponse instance
     */
    public static ErrorHttpResponse createError404(Socket socket) {
        return new ErrorHttpResponse(socket, "404 File Not Found", "<h1>ERROR 404 - File Not Found!</h1>");

    }

    /**
     * Creates an Instance of this class representing a 500 Error
     * @param socket the socket this Error will be send to
     * @return the ErrorHttpResponse instance
     */
    public static ErrorHttpResponse createError500 (Socket socket) {
        return new ErrorHttpResponse(socket, "500 Internal Server Error", "<h1>ERROR 500 - Internal Server Error</h1>");
    }

    @Override
    protected byte[] getDataBytes() {
        return errorMessage.getBytes();
    }

    @Override
    protected byte[] getHeaderBytes() {
        return header.getBytes();
    }

    @Override
    protected byte[] getStatusCodeBytes() {
        return statusCode.getBytes();
    }
}

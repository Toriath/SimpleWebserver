package de.adesso.tokl.webserver.sockets.http.response.error;

import de.adesso.tokl.webserver.sockets.http.response.HttpResponse;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 */
public abstract class ErrorHttpResponse extends HttpResponse {

    private final String errorMessage;
    private final String statusCode;
    private final String header;

    public ErrorHttpResponse(Socket socket, String errorCode, String errorMessage) {
        super(socket);
        this.errorMessage = errorMessage;
        this.statusCode = "HTTP/1.1 " + errorCode + "\r\n";
        this.header = "Content-Type: text/html\r\n" + "Content-Length: " + errorMessage.length() + "\r\n\r\n";

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

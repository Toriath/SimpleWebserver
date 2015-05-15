package de.adesso.tokl.webserver.sockets.http.response;

import de.adesso.tokl.webserver.sockets.http.request.RequestedFile;
import lombok.extern.log4j.Log4j2;

import java.net.Socket;
import java.util.Date;

/**
 * Created by kloss on 13.05.2015.
 * A Response sending a requested file
 */
@Log4j2
public class FileHttpResponse extends HttpResponse {

    private final RequestedFile requestedFile;

    /**
     * Consructs a HttpResponse which response data is a file.
     * @param socket the socket to respond to
     * @param requestedFile the file being requested.
     */
    public FileHttpResponse(Socket socket, RequestedFile requestedFile) {
        super(socket);
        this.requestedFile = requestedFile;
    }

    @Override
    protected byte[] getDataBytes() {
       return requestedFile.getBytes();
    }

    @Override
    protected byte[] getHeaderBytes() {
        String contentType = requestedFile.getContentType();
        String information =  "Content-Type: " + contentType + "\r\n" +
                "Date: " + new Date() + "\r\n" +
                "Server: SimpleWebserver 1.0\r\n\r\n";
        return information.getBytes();
    }

    @Override
    protected byte[] getStatusCodeBytes() {
        return "HTTP/1.1 200 OK\r\n".getBytes();
    }
}

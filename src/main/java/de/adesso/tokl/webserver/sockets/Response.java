package de.adesso.tokl.webserver.sockets;

import de.adesso.tokl.webserver.sockets.configuration.HttpHeader;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Represents a HTTP reponse to a given HTTP request.
 */
@Log4j2
class Response {

    private static final int BUFFER_SIZE = 2048;
    private final OutputStream output;
    private Request request;

    /**
     * Constructor for a Response to a HTTP Request.
     *
     * @param output        the output stream of the socket that was used to create the request
     * @param request
     */
    public Response(OutputStream output, Request request) {
        this.output = output;
        this.request = request;
    }

    /**
     * Checks whether or not the file exists and then either returns an error message or the file     *
     *
     * @throws IOException if the file input stream can not be closed
     */
    public void sendResponse() throws IOException {
        try {
            File requestedFile = request.getRequestedFile();
            if(requestedFile.exists()){
                sendHttpHeader();
                sendFile(requestedFile);
            } else {
                log.error("Requested file was not found: " + requestedFile.getPath());
                sendError(HttpError.ERROR_404);
            }
        } catch (IOException e) {
            log.catching(e);
            sendError(HttpError.ERROR_500);
        }
    }

    /**
     * Sends the given file to the client
     *
     * @param requestedFile the requestedFile to send as response
     * @throws IOException in case the file can not be send to the client
     */
    private void sendFile(File requestedFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(requestedFile);

        byte[] bytes = new byte[BUFFER_SIZE];
        int ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);

        while (ch != -1) {
            output.write(bytes, 0, ch);
            ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);
        }

        fileInputStream.close();
    }

    /**
     * Send a 404 error page to the requesting client
     *
     * @param error the HttpError to send
     * @throws IOException when is is not possible to write to the output stream
     */
    private void sendError(HttpError error) throws IOException {
        output.write(error.getBytes());
    }

    /**
     * Sends the HTTP header to define the Mime Tag, Date and Servername
     *
     * @param uri The uri of the file to send
     * @throws IOException in case the bytes can not be written to the output stream
     */
    private void sendHttpHeader() throws IOException {
        HttpHeader header = new HttpHeader(request.getUri(), "SimpleServer 1.0");
        output.write(header.getBytes());
    }


}

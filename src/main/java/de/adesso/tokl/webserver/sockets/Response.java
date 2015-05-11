package de.adesso.tokl.webserver.sockets;

import de.adesso.tokl.webserver.sockets.configuration.HttpHeader;
import lombok.extern.log4j.Log4j2;

import java.io.*;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Represents a HTTP reponse to a given HTTP request.
 */
@Log4j2
class Response {

    private final OutputStream output;
    private Request request;

    /**
     * Constructor for a Response to a HTTP Request.
     *
     * @param output  the output stream of the socket that was used to create the request
     * @param request the request to answer to
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
    public void answerRequest() throws IOException {
        try {
            File requestedFile = request.getRequestedFile();
            if (requestedFile.exists()) {
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
        byte[] requestedFileBytes = new byte[(int) requestedFile.length()];

        FileInputStream fileInputStream = new FileInputStream(requestedFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        bufferedInputStream.read(requestedFileBytes, 0, requestedFileBytes.length);

        sendBytes(requestedFileBytes);

        fileInputStream.close();
        bufferedInputStream.close();
    }

    /**
     * Send an error page to the requesting client
     *
     * @param error the HttpError to send
     * @throws IOException when it is not possible to write to the output stream
     */
    private void sendError(HttpError error) throws IOException {
        sendBytes(error.getBytes());
    }

    /**
     * Sends the HTTP header to define the Mime Tag, Date and Servername
     *
     * @param uri The uri of the file to send
     * @throws IOException in case the bytes can not be written to the output stream
     */
    private void sendHttpHeader() throws IOException {
        HttpHeader header = new HttpHeader(request.getUri(), "SimpleServer 1.0");
        sendBytes(header.getBytes());
    }

    /**
     * Sends bytes to the output stream and flushes
     *
     * @param bytes the bytes to send
     * @throws IOException in case the writing or flushing fails
     */
    private void sendBytes(byte[] bytes) throws IOException {
        output.write(bytes);
        output.flush();
    }

}

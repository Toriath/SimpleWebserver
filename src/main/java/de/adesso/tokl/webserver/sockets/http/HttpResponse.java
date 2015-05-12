package de.adesso.tokl.webserver.sockets.http;

import lombok.extern.log4j.Log4j2;

import java.io.*;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Represents a HTTP reponse to a given HTTP request.
 */
@Log4j2
public class HttpResponse {

    private final OutputStream output;
    private HttpRequest httpRequest;

    /**
     * Constructor for a Response to a HTTP Request.
     *
     * @param output  the output stream of the socket that was used to create the request
     * @param httpRequest the request to answer to
     */
    public HttpResponse(OutputStream output, HttpRequest httpRequest) {
        this.output = output;
        this.httpRequest = httpRequest;
    }

    /**
     * Checks whether or not the file exists and then either returns an error message or the file     *
     *
     * @throws IOException if the file input stream can not be closed
     */
    public void answerRequest() throws IOException {
        try {
            File requestedFile = httpRequest.getRequestedFile();
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
        log.info("Sending file " + requestedFile.getPath());
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
        log.info("Sending HttpErrpr " + error.name());
        sendBytes(error.getBytes());
    }

    /**
     * Can be used to redirect a client to another site
     * @param url The url to redirect to
     * @throws IOException in case the redirect can not be sent to the output stream
     */
    private void sendRedirect(String url) throws IOException {
        String redirect = "HTTP/1.1 307 Temporary Redirect\r\nLocation: " + url;
        sendBytes(redirect.getBytes());
    }

    /**
     * Sends the HTTP header to define the Mime Tag, Date and Servername
     *
     * @throws IOException in case the bytes can not be written to the output stream
     */
    private void sendHttpHeader() throws IOException {
        log.info("Sending http header");
        HttpHeader header = new HttpHeader(httpRequest.getUri(), "SimpleServer 1.0");
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

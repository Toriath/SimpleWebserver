package de.adesso.tokl.webserver.sockets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Date;

import static java.nio.file.Files.probeContentType;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Represents a HTTP reponse to a given HTTP request.
 */
class Response {

    private static final int BUFFER_SIZE = 2048;
    private final Logger logger = LogManager.getLogger(Response.class);
    private final OutputStream output;
    private final String rootDirectory;
    private Request request;

    public Response(OutputStream output, String rootDirectory) {
        this.output = output;
        this.rootDirectory = rootDirectory;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Checks whether or not the file exists and then either returns an error message or the file     *
     *
     * @throws IOException if the file input stream can not be closed
     */
    public void sendStaticResource() throws IOException {

        if (request.getUri() == null) return;
        try {
            File file = new File(rootDirectory, request.getUri());
            if (file.exists()) {
                sendHeader(request.getUri());
                sendFile(file);
            } else {
                sendError(HttpError.ERROR_404);
            }
        } catch (IOException e) {
            logger.catching(e);
            sendError(HttpError.ERROR_500);
        }
    }

    /**
     * Sends the given file to the client
     *
     * @param file The file to send to the client via http
     * @throws IOException in case the file can not be send to the client
     */
    private void sendFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[BUFFER_SIZE];
        int ch = fis.read(bytes, 0, BUFFER_SIZE);

        while (ch != -1) {
            output.write(bytes, 0, ch);
            ch = fis.read(bytes, 0, BUFFER_SIZE);
        }

        fis.close();

    }

    /**
     * Send a 404 error page to the requesting client
     *
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
    private void sendHeader(String uri) throws IOException {
        String contentType = probeContentType(Paths.get(uri));
        byte[] httpHeaderBytes = ("HTTP/1.0 200 OK\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Date: " + new Date() + "\r\n" +
                "Server: SimpleWebserver 1.0\r\n\r\n").getBytes();
        output.write(httpHeaderBytes);
    }


}

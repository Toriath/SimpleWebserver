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
public class Response {

    Logger logger = LogManager.getLogger(Response.class);

    private static final String ERROR_MESSAGE = "HTTP/1.1 404 File Not Found\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: 23\r\n" +
            "\r\n" +
            "<h1>File Not Found</h1>";
    private static final int BUFFER_SIZE = 2048;
    private Request request;
    private OutputStream output;
    private String rootDirectory;

    public Response(OutputStream output, String rootDirectory) {
        this.output = output;
        this.rootDirectory = rootDirectory;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Checks whether or not the file exists and then either returns an error message or the file     *
     * @throws IOException if the file object can not be instantiated
     */
    public void sendStaticResource() throws IOException {

        FileInputStream fis = null;
        if(request.getUri() == null) return;
        try {
            File file = new File(rootDirectory, request.getUri());
            if (file.exists()) {
                sendHeader(request.getUri());

                fis = new FileInputStream(file);
                byte[] bytes = new byte[BUFFER_SIZE];
                int ch = fis.read(bytes, 0, BUFFER_SIZE);

                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
                output.write(ERROR_MESSAGE.getBytes());
            }
        } catch (IOException e) {
            logger.catching(e);
        } finally {
            if (fis != null) fis.close();
        }
    }

    /**
     * Sends the HTTP header to define the Mime Tag, Date and Servername
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

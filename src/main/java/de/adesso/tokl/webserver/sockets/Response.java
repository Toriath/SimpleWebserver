package de.adesso.tokl.webserver.sockets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Represents a HTTP reponse to a given HTTP request.
 */
public class Response {

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
     * Checks wheter or not the file exists and then either returns an error message or the file
     *
     * @throws IOException if the file object can not be instantiated
     */
    public void sendStaticResource() throws IOException {

        FileInputStream fis = null;
        if(request.getUri() == null) return;
        try {
            File file = new File(rootDirectory, request.getUri());
            if (file.exists()) {
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
        } catch (IOException ioException) {
            System.out.println(ioException.toString());
        } finally {
            if (fis != null) fis.close();
        }
    }


}

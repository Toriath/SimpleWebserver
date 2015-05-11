package de.adesso.tokl.webserver.sockets.http;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Respresents a HTTP request
 */

@Log4j2
public class HttpRequest {


    private final InputStream input;
    private String rootDirectory;
    @Getter
    private String uri;

    /**
     * Constructor for a Request by a given input stream
     *
     * @param input The Inputstream to read the request from
     * @param rootDirectory the root directory of the server to look for files
     */
    public HttpRequest(InputStream input, String rootDirectory) {
        this.input = input;
        this.rootDirectory = rootDirectory;
        parse();
    }

    /**
     * Parses the input stream and saves the uri of the requested file
     */
    private void parse() {
        // Read a set of characters from the socket
        StringBuilder request = new StringBuilder(2048);
        int i;
        byte[] buffer = new byte[2048];

        try {
            i = input.read(buffer);
        } catch (IOException e) {
            log.catching(e);
            i = -1;
        }

        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }


        uri = parseUri(request.toString());
    }

    /**
     * Cuts the uri out of an HTTP Request
     *
     * @param requestString the http request as string
     * @return the URI of the requests file as string
     */
    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');

        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return requestString.substring(index1 + 1, index2);
            }
        }

        return null;
    }

    /**
     * Returns the requested file. In case of directories it redirects to the index.html.
     * @return the requested file
     */
    public File getRequestedFile(){
        File requestedFile = new File(rootDirectory, getUri());
        requestedFile = redirectToIndex(requestedFile);
        return requestedFile;
    }

    /**
     * Checks if a file is a directory and redirects to an index.html if true
     *
     * @param file the file to check
     * @return the redirected file. If the original file was not a file it is returned without being changed.
     */
    private File redirectToIndex(File file) {
        if (file.isDirectory()) {
            file = new File(file.getPath() + File.separator + "index.html");
        }
        return file;
    }
}

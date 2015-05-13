package de.adesso.tokl.webserver.sockets.http.request;

import de.adesso.tokl.webserver.sockets.http.InternalServerErrorException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Respresents a HTTP request
 */

@Log4j2
public class HttpRequest {


    private InputStream input;

    @Getter
    private String uri;

    /**
     * Constructor for a Request by a given input stream
     *
     * @param socket The socket this request is created for
     */
    public HttpRequest(Socket socket) {
        try {
            this.input = socket.getInputStream();
        } catch (IOException e) {
            log.catching(e);
            throw new InternalServerErrorException("Failed to get the inputstream from the socket", e);
        }
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




}

package de.adesso.tokl.webserver.sockets.http.response.error;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 * Subclass of ErrorHttpRespond that implement the behavior of a 500 Error
 */
public class Error500HttpResponse extends ErrorHttpResponse {
    /**
     * Constructs a Http500Error
     * @param socket the socket to respond to
     */
    public Error500HttpResponse(Socket socket) {
        super(socket, "500 Internal Server Error", "<h1>ERROR 500 - Internal Server Error</h1>");
    }
}

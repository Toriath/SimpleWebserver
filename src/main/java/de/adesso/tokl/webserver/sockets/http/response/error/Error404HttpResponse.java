package de.adesso.tokl.webserver.sockets.http.response.error;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 * Subclass of ErrorHttpRespond that implement the behavior of a 404 Error
 */
public class Error404HttpResponse extends ErrorHttpResponse {
    /**
     * Constructs a Http404Error
     * @param socket the socket to respond to
     */
    public Error404HttpResponse(Socket socket) {
        super(socket, "404 File Not Found", "<h1>ERROR 404 - File Not Found!</h1>");
    }
}

package de.adesso.tokl.webserver.sockets.http.response.error;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 */
public class Error500HttpResponse extends ErrorHttpResponse {
    public Error500HttpResponse(Socket socket) {
        super(socket, "500 Internal Server Error", "<h1>ERROR 500 - Internal Server Error</h1>");
    }
}

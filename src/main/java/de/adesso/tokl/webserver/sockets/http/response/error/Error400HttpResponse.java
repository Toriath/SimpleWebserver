package de.adesso.tokl.webserver.sockets.http.response.error;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 */
public class Error400HttpResponse extends ErrorHttpResponse {
    public Error400HttpResponse(Socket socket) {
        super(socket, "404 File Not Found", "<h1>ERROR 404 - File Not Found!</h1>");
    }
}

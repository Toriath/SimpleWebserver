package de.adesso.tokl.webserver.sockets.http.response.error;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 */
public class Error404HttpResponse extends ErrorHttpResponse {
    public Error404HttpResponse(Socket socket) {
        super(socket, "404 File Not Found", "<h1>ERROR 404 - File Not Found!</h1>");
    }
}

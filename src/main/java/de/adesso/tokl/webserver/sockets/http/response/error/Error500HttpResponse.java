package de.adesso.tokl.webserver.sockets.http.response.error;

import de.adesso.tokl.webserver.sockets.http.response.HttpResponse;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 */
public class Error500HttpResponse extends HttpResponse {
    public Error500HttpResponse(Socket socket) {
        super(socket);
    }
//TODO: Implement
    @Override
    protected byte[] getDataBytes() {
        return new byte[0];
    }

    @Override
    protected byte[] getHeaderBytes() {
        return new byte[0];
    }

    @Override
    protected byte[] getStatusCodeBytes() {
        return new byte[0];
    }
}

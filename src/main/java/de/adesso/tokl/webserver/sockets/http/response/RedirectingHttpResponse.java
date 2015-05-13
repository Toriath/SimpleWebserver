package de.adesso.tokl.webserver.sockets.http.response;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 * Redirects a client to another url
 */
public class RedirectingHttpResponse extends HttpResponse {

    private final String redirectUrl;

    public RedirectingHttpResponse(Socket socket, String redirectUrl) {
        super(socket);
        this.redirectUrl = redirectUrl;
    }
//TODO: Implement
    @Override
    protected byte[] getDataBytes() {
        return "".getBytes();
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

package de.adesso.tokl.webserver.sockets.http.response;

import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 * Redirects a client to another url
 */
public class RedirectingHttpResponse extends HttpResponse {

    private final String redirectUrl;

    /**
     * Creates a response to redirect the requesting client to another url
     * @param socket the socket to respond to
     * @param redirectUrl the Url to redirect to
     */
    public RedirectingHttpResponse(Socket socket, String redirectUrl) {
        super(socket);
        this.redirectUrl = redirectUrl;
    }
    @Override
    protected byte[] getDataBytes() {
        return "".getBytes();
    }

    @Override
    protected byte[] getHeaderBytes() {
        return ("Location: " + redirectUrl + "\r\n").getBytes();
    }

    @Override
    protected byte[] getStatusCodeBytes() {
        return "HTTP/1.1 307 Temporary Redirect\r\n".getBytes();
    }
}

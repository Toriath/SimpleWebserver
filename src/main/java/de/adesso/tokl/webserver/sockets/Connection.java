package de.adesso.tokl.webserver.sockets;

import de.adesso.tokl.webserver.sockets.http.InternalServerErrorException;
import de.adesso.tokl.webserver.sockets.http.request.HttpRequest;
import de.adesso.tokl.webserver.sockets.http.request.RequestedFile;
import de.adesso.tokl.webserver.sockets.http.response.FileHttpResponse;
import de.adesso.tokl.webserver.sockets.http.response.HttpResponse;
import de.adesso.tokl.webserver.sockets.http.response.RedirectingHttpResponse;
import de.adesso.tokl.webserver.sockets.http.response.error.Error404HttpResponse;
import de.adesso.tokl.webserver.sockets.http.response.error.Error500HttpResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Represents an ongoing connection to a requesting client
 */
@Log4j2
class Connection implements Runnable {


    private final Socket socket;
    private final String rootDirectory;
    private Map<String, String> redirects = new HashMap<String, String>();

    /**
     * Constructor for an incoming connection
     *
     * @param socket        The given socket provided by the Server
     * @param rootDirectory the root directory of the server
     */
    public Connection(Socket socket, String rootDirectory) {
        this.socket = socket;
        this.rootDirectory = rootDirectory;
    }

    /**
     * Creates a Request and sends a Response to the client
     */
    public void run() {
        HttpResponse response = null;
        try {
            HttpRequest httpRequest = new HttpRequest(socket);
            response = chooseResponseType(httpRequest);
        } catch (InternalServerErrorException e) {
            log.catching(e);
            log.error("Internal Server error");
            response = new Error500HttpResponse(socket);
        } finally {
            if (response == null) {
                response = new Error500HttpResponse(socket);
            }
            response.sendResponse();
        }


        try {
            socket.close();
        } catch (IOException e) {
            log.catching(e);
        }
    }

    /**
     * Chooses how to respond to a given request
     *
     * @param httpRequest The request to answer
     * @return An HttpResponse Subclass that provides the correct type of response
     */
    private HttpResponse chooseResponseType(HttpRequest httpRequest) {

        String uri = httpRequest.getUri();
        RequestedFile requestedFile = new RequestedFile(rootDirectory, uri);

        if (isRedirect(uri)) {
            return new RedirectingHttpResponse(socket, getRedirect(uri));
        } else if (requestedFile.exists()) {
            return new FileHttpResponse(socket, requestedFile);
        } else {
            return new Error404HttpResponse(socket);
        }

    }

    /**
     * Gets the new URL for a redirect
     *
     * @param uri the URI being called
     * @return the URL the uri is redirected to
     */
    private String getRedirect(String uri) {
        return redirects.get(uri);
    }

    /**
     * Checks if a given uri is a redirect or not
     *
     * @param uri the uri to check
     * @return true if the uri should be redirected, otherwise false
     */
    private boolean isRedirect(String uri) {
        return redirects.containsKey(uri);
    }


}

package de.adesso.tokl.webserver.sockets;

import de.adesso.tokl.webserver.sockets.http.InternalServerErrorException;
import de.adesso.tokl.webserver.sockets.http.request.HttpRequest;
import de.adesso.tokl.webserver.sockets.http.request.RequestedFile;
import de.adesso.tokl.webserver.sockets.http.response.FileHttpResponse;
import de.adesso.tokl.webserver.sockets.http.response.HttpResponse;
import de.adesso.tokl.webserver.sockets.http.response.error.Error400HttpResponse;
import de.adesso.tokl.webserver.sockets.http.response.error.Error500HttpResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Represents an ongoing connection to a requesting client
 */
@Log4j2
class Connection implements Runnable {


    private final Socket socket;
    private final String rootDirectory;

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
            response.sendResponse();
        }


        try {
            socket.close();
        } catch (IOException e) {
            log.catching(e);
        }
    }

    private HttpResponse chooseResponseType(HttpRequest httpRequest) {

        RequestedFile requestedFile = new RequestedFile(rootDirectory, httpRequest.getUri());
        //TODO: Check for bad request
        //TODO: Check for redirects
        if (requestedFile.exists()) {
            return new FileHttpResponse(socket, requestedFile);
        } else {
            return new Error400HttpResponse(socket);
        }

    }


    private boolean isRedirect(String uri) {
        return false; //TODO: implement
    }


}

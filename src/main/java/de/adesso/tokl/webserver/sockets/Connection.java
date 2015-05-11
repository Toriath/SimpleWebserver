package de.adesso.tokl.webserver.sockets;

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
        try {
            Request request = new Request(socket.getInputStream());
            answerRequest(request);
            socket.close();

        } catch (IOException e) {
            log.catching(e);
        }
    }

    /**
     * Answers the given request by creating a response object
     *
     * @param request the request to answer
     * @throws IOException of the response can not be sent to the socket.
     */
    private void answerRequest(Request request) throws IOException {
        Response response = new Response(socket.getOutputStream(), rootDirectory);
        response.setRequest(request);
        response.sendStaticResource();
    }


}

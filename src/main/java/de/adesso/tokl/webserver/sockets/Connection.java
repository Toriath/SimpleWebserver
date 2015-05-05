package de.adesso.tokl.webserver.sockets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by kloss on 30.04.2015.
 *
 * Represents an ongoing connection to a requesting client
 */
class Connection implements Runnable {

    private final Logger logger = LogManager.getLogger(Connection.class);
    private final Socket socket;
    private final String rootDirectory;

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
            sendResponseForRequest(socket.getOutputStream(), request);
            socket.close();

        } catch (IOException e) {
            logger.catching(e);
        }
    }

    private void sendResponseForRequest(OutputStream output, Request request) throws IOException {
        Response response = new Response(output, rootDirectory);
        response.setRequest(request);
        response.sendStaticResource();
    }



}

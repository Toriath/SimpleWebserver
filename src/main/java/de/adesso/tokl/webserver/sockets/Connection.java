package de.adesso.tokl.webserver.sockets;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by kloss on 30.04.2015.
 *
 * Represents an ongoing connection to a requesting client
 */
@Log4j2
class Connection implements Runnable {

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
            log.catching(e);
        }
    }

    private void sendResponseForRequest(OutputStream output, Request request) throws IOException {
        Response response = new Response(output, rootDirectory);
        response.setRequest(request);
        response.sendStaticResource();
    }



}

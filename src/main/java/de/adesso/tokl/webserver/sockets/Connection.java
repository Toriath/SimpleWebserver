package de.adesso.tokl.webserver.sockets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
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

    public void run() {

        InputStream input;
        OutputStream output;

        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();

            Request request = new Request(input);

            sendResponseForRequest(output, request);

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

package de.adesso.tokl.webserver.sockets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by kloss on 30.04.2015.
 */
public class Connection implements Runnable {

    Logger logger = LogManager.getLogger(Connection.class);
    Socket socket;
    private String rootDirectory;

    public Connection(Socket socket, String rootDirectory) {
        this.socket = socket;
        this.rootDirectory = rootDirectory;
    }

    public void run() {

        InputStream input = null;
        OutputStream output = null;

        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();

            // create Request object and parse
            Request request = new Request(input);
            request.parse();

            // create Response object
            Response response = new Response(output, rootDirectory);
            response.setRequest(request);
            response.sendStaticResource();

            // Close the socket
            socket.close();

        } catch (IOException e) {
            logger.catching(e);
        }

    }

}

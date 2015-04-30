package de.adesso.tokl.webserver.sockets;

import de.adesso.tokl.webserver.WebServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kloss on 29.04.2015.
 * <p>
 * A Simple HttpServer that returns Static HTML Pages and Images on HTTP requests
 */
public class SocketsWebServer implements WebServer {

    private String rootDirectory; //TODO Make this configurable
    private int serverPort; //TODO Make this configurable
    private boolean running;

    public SocketsWebServer(String rootDirectory, int serverPort) {
        this.rootDirectory = rootDirectory;
        this.serverPort = serverPort;
    }

    public SocketsWebServer() {
        this(System.getProperty("user.home") + File.separator + "SimpleWebserver", 8080);
    }

    public void await() {
        running = true;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(serverPort, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Loop waiting for a request
        while (running) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
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


            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }


}

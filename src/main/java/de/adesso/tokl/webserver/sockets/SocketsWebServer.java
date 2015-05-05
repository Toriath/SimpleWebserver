package de.adesso.tokl.webserver.sockets;

import de.adesso.tokl.webserver.WebServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kloss on 29.04.2015.
 * <p>
 * A Simple HttpServer that returns Static HTML Pages and Images on HTTP requests
 */
public class SocketsWebServer implements WebServer {

    private Logger logger = LogManager.getLogger(SocketsWebServer.class);
    private final ExecutorService executor;
    private final String rootDirectory; //TODO Make this configurable
    private final int serverPort; //TODO Make this configurable
    private boolean running;

    public SocketsWebServer(String rootDirectory, int serverPort) {
        this.rootDirectory = rootDirectory;
        this.serverPort = serverPort;
        executor = new ThreadPoolExecutor(20, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));
    }

    public SocketsWebServer() {
        this(System.getProperty("user.home") + File.separator + "SimpleWebserver", 8080);
    }

    /**
     * Starts the Server on the configured Port and waits for requests
     */
    public void await() {
        logger.trace("Server is booting...");

        running = true;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(serverPort, 20, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            logger.catching(e);
            System.exit(1);
        }

        while (running) {
            Socket socket = null;
            try {
                logger.trace("Server is waiting for requests...");
                socket = serverSocket.accept();
                logger.info("Request recieved from " + socket.getInetAddress());
                Connection connection = new Connection(socket, rootDirectory);
                executor.execute(connection);
            } catch (IOException e) {
                logger.catching(e);
                continue;
            }
        }

    }


}

package de.adesso.tokl.webserver.sockets;

import de.adesso.tokl.webserver.sockets.configuration.ServerConfiguration;
import lombok.extern.log4j.Log4j2;

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

@Log4j2
public class SocketsWebServer implements WebServer {

    private final ExecutorService executor = new ThreadPoolExecutor(20, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));
    private ServerConfiguration config;
    private ServerSocket serverSocket;
    private boolean running;

    /**
     * Creates a webserver wich uses the given configuration
     *
     * @param config the configuration for the server
     */
    public SocketsWebServer(final ServerConfiguration config) {
        this.config = config;
        logConfiguration();
        createDirectories();
        setupServerSocket();
    }

    /**
     * Logs the configurations which is actively applied to the sever after startup
     */
    private void logConfiguration() {
        log.info("Server port set to: " + config.getServerPort());
        log.info("Root directory set to: " + config.getRootDirectory());
    }

    /**
     * Creates the directories for the server.
     */
    private void createDirectories() {
        log.trace("Server is creating needed directories");
        File rootDir = new File(config.getRootDirectory());
        boolean success = rootDir.mkdirs();
        if (!success) {
            log.error("Server did not create all needed directories");
        }
    }

    /**
     * Starts the Server on the configured Port and waits for requests
     */
    public void await() {
        log.trace("Server is starting up");

        running = true;

        while (running) {
            Socket socket;
            try {
                log.trace("Waiting for requests...");
                socket = serverSocket.accept();

                Connection connection = new Connection(socket, config.getRootDirectory());//NOPMD
                executor.execute(connection);

            } catch (IOException e) {
                log.catching(e);
            }
        }

    }

    /**
     * Initializes the server socket with the given configuration
     */
    private void setupServerSocket() {
        try {
            serverSocket = new ServerSocket(config.getServerPort(), 20, InetAddress.getByName("127.0.0.1"));//NOPMD
        } catch (IOException e) {
            log.catching(e);
            throw new RuntimeException("Server socket could not be created", e);

        }
    }


}

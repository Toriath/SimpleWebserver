package de.adesso.tokl.webserver;

import de.adesso.tokl.webserver.sockets.ServerConfiguration;
import de.adesso.tokl.webserver.sockets.SocketsWebServer;

import java.io.IOException;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Startup class to initialise the Server
 */
class Startup {


    public static void main(String[] args) {

        ServerConfiguration config = null;

        try {
            config = new ServerConfiguration("serverConfig.properties");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        WebServer server = new SocketsWebServer(config);
        server.await();

    }
}

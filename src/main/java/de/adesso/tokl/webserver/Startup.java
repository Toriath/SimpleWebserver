package de.adesso.tokl.webserver;

import de.adesso.tokl.webserver.sockets.SocketsWebServer;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Startup class to initialise the Server
 */
class Startup {

    public static void main(String[] args) {

        WebServer server = new SocketsWebServer();
        server.await();

    }
}

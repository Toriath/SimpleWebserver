package de.adesso.tokl.webserver.httpserver;

import com.sun.net.httpserver.HttpServer;
import de.adesso.tokl.webserver.WebServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by kloss on 30.04.2015.
 */
class JavaWebServer implements WebServer {

    HttpServer server;

    public JavaWebServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(8080), 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void await() {
        server.start();
    }
}

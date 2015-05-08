package de.adesso.tokl.webserver.sockets.configuration;

/**
 * Created by kloss on 08.05.2015.
 *
 * Interface representing a configuration for the server
 */
public interface ServerConfiguration {

    int getServerPort();

    String getRootDirectory();
}

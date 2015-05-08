package de.adesso.tokl.webserver.sockets.configuration;

/**
 * Created by kloss on 08.05.2015. *
 * Interface representing a configuration for the server
 */
public interface ServerConfiguration {

    /**
     * Get the configured server port
     * @return the server port as int
     */
    int getServerPort();

    /**
     * Get the configured server root directory
     * @return the root directory path as string
     */
    String getRootDirectory();
}

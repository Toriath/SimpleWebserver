package de.adesso.tokl.webserver.sockets.configuration;

import java.io.File;

/**
 * Created by kloss on 08.05.2015.
 *
 * The Default configuration for the Webserver.
 */
public class DefaultConfiguration implements ServerConfiguration {

    /**
     * The default server port configuration
     */
    public static final int DEFAULT_SERVER_PORT = 8080;
    /**
     * The default root directory configuration
     */
    public static final String DEFAULT_ROOT_DIR = System.getProperty("user.home") + File.separator + "SimpleWebserver" + File.separator ;


    public int getServerPort() {
        return DEFAULT_SERVER_PORT;
    }

    public String getRootDirectory() {
        return DEFAULT_ROOT_DIR;
    }
}

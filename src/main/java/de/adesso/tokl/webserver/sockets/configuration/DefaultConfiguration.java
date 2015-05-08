package de.adesso.tokl.webserver.sockets.configuration;

import java.io.File;

/**
 * Created by kloss on 08.05.2015.
 */
public class DefaultConfiguration implements ServerConfiguration {

    public static final int DEFAULT_SERVER_PORT = 8080;
    public static final String DEFAULT_ROOT_DIR = System.getProperty("user.home") + File.separator + "SimpleWebserver" ;


    public int getServerPort() {
        return DEFAULT_SERVER_PORT;
    }

    public String getRootDirectory() {
        return DEFAULT_ROOT_DIR;
    }
}

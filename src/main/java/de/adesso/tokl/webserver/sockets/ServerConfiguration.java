package de.adesso.tokl.webserver.sockets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by kloss on 05.05.2015.
 * <p>
 * The Configuration for a SimpleWebServer
 */
public class ServerConfiguration {

    private static final String DEFAULT_ROOT_DIR = System.getProperty("user.home") + File.separator + "SimpleWebserver";

    Properties properties = new Properties();

    public ServerConfiguration(String pathToPropertiesFile) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pathToPropertiesFile);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("The givec properties file could not be found");
        }
    }

    public int getServerPort() {
        return Integer.parseInt(properties.getProperty("port", "8080"));
    }

    public String getRootDirectory(){
           return properties.getProperty("rootDirectory", DEFAULT_ROOT_DIR);
    }
}

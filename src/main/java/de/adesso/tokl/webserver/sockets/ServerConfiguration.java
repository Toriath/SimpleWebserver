package de.adesso.tokl.webserver.sockets;

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

    Properties properties = new Properties();

    public ServerConfiguration(String pathToPropertiesFile) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pathToPropertiesFile);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("The givec properties file could not be found");
        }
    }

    private String getConfigByName(String name) {
        return properties.getProperty(name);
    }
}

package de.adesso.tokl.webserver.sockets.configuration;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.Properties;

/**
 * Created by kloss on 05.05.2015.
 * <p>
 * The Configuration for a SimpleWebServer
 */
@Log4j2
public class PropertyConfiguration implements ServerConfiguration {

    Properties properties = new Properties();

    public PropertyConfiguration(File propertiesFile) {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(propertiesFile);
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (FileNotFoundException e) {
            throw new ConfigurationException("The given properties file could not be found.");

        } catch (IOException e) {
            throw new ConfigurationException("Failed to read the properties file.");
        }
    }


    public int getServerPort() {
        return Integer.parseInt(properties.getProperty("port", String.valueOf(DefaultConfiguration.DEFAULT_SERVER_PORT)));
    }

    public String getRootDirectory() {
        return properties.getProperty("rootDirectory", DefaultConfiguration.DEFAULT_ROOT_DIR);
    }
}

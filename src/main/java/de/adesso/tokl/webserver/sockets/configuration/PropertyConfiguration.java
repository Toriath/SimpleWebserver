package de.adesso.tokl.webserver.sockets.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by kloss on 05.05.2015.
 * <p>
 * The Configuration for a SimpleWebServer
 */
public class PropertyConfiguration implements ServerConfiguration {

    private Logger logger = LogManager.getLogger(PropertyConfiguration.class);
    Properties properties = new Properties();

    public PropertyConfiguration(File propertiesFile){

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(propertiesFile);
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (FileNotFoundException e) {
            logger.catching(e);
            logger.error("The given properties file could not be found.");
            System.exit(1);
        } catch (IOException e) {
            logger.catching(e);
            logger.error("Failed to read the properties file.");
            System.exit(1);
        }
    }


    public int getServerPort() {
        return Integer.parseInt(properties.getProperty("port", String.valueOf(DefaultConfiguration.DEFAULT_SERVER_PORT)));
    }

    public String getRootDirectory() {
        return properties.getProperty("rootDirectory", DefaultConfiguration.DEFAULT_ROOT_DIR);
    }
}

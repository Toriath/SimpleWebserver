package de.adesso.tokl.webserver.sockets;

import java.io.*;
import java.util.Properties;

/**
 * Created by kloss on 05.05.2015.
 * <p>
 * The Configuration for a SimpleWebServer
 */
public class ServerConfiguration {

    public static final String USER_PROPERTIES_PATH = System.getProperty("user.home") + File.separator + "SimpleWebserver" + File.separator + "serverConfig.properties";
    private static final String DEFAULT_ROOT_DIR = System.getProperty("user.home") + File.separator + "SimpleWebserver" + File.separator + "rootDir";
    Properties properties = new Properties();

    public ServerConfiguration() throws IOException {

        InputStream inputStream = getInputStream();

        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("The given properties file could not be found");
        }

        createRootDir();
    }

    private void createRootDir() {
        File rootDir = new File(DEFAULT_ROOT_DIR);
        rootDir.mkdirs();
    }

    private InputStream getInputStream() throws FileNotFoundException {
        File propertiesFile = new File(USER_PROPERTIES_PATH);
        InputStream inputStream;


        inputStream = new FileInputStream(propertiesFile);

        return inputStream;
    }

    public int getServerPort() {
        return Integer.parseInt(properties.getProperty("port", "8080"));
    }

    public String getRootDirectory() {
        return properties.getProperty("rootDirectory", DEFAULT_ROOT_DIR);
    }
}

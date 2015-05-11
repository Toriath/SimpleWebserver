package de.adesso.tokl.webserver;

import de.adesso.tokl.webserver.sockets.SocketsWebServer;
import de.adesso.tokl.webserver.sockets.WebServer;
import de.adesso.tokl.webserver.sockets.configuration.*;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Startup class to initialise the Server
 */
@Log4j2
class ServerLauncher {

    /**
     * Constructor for the server launcher.
     * @param args the same argument array as given by the main method.
     */
    public void launch(String[] args) {
        ServerConfiguration config = null;

        log.trace("Checking for server configuration.");

        try {
            if (args.length != 0) {
                config = new CommandLineConfiguration(args);
                log.info("Commandline configuration loaded!");
            }

            File propertiesFile = getPropertiesFile();
            if (config == null) {
                log.trace("No command line parameters given. Using properties instead.");
                if (propertiesFile.exists()) {
                    config = new PropertyConfiguration(propertiesFile);
                    log.info("Property configuration loaded!");
                }
            }
        } catch (ConfigurationException e) {
            log.catching(e);
        }

        if (config == null) {
            log.trace("No configuration found. Falling back to default settings.");
            config = new DefaultConfiguration();
            log.info("Default configuration loaded!");
        }


        WebServer server = new SocketsWebServer(config);
        server.await();

    }

    /**
     * Searches for a property file next to the jar
     * @return the property file as file or null if the properties file does not exist.
     */
    private File getPropertiesFile() {
        File file = null;
        CodeSource src = PropertyConfiguration.class.getProtectionDomain().getCodeSource();
        if (src != null) {
            try {
                URL url = new URL(src.getLocation(), "serverConfig.properties");
                file = new File(url.getPath());
            } catch (MalformedURLException e) {
                log.catching(e);
            }
        }
        return file;
    }


}

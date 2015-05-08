package de.adesso.tokl.webserver;

import de.adesso.tokl.webserver.sockets.SocketsWebServer;
import de.adesso.tokl.webserver.sockets.configuration.CommandLineConfiguration;
import de.adesso.tokl.webserver.sockets.configuration.DefaultConfiguration;
import de.adesso.tokl.webserver.sockets.configuration.PropertyConfiguration;
import de.adesso.tokl.webserver.sockets.configuration.ServerConfiguration;
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

    public void launch(String[] args){
        ServerConfiguration config = null;

        log.trace("Checking for server configuration.");

        if(args.length != 0){
            config = new CommandLineConfiguration(args);
            log.info("Commandline configuration loaded!");
        }

        File propertiesFile = getPropertiesFile();

        if(config == null){
            log.trace("No command line parameters given. Using properties instead.");
            if(propertiesFile.exists()){
                config = new PropertyConfiguration(propertiesFile);
                log.info("Property configuration loaded!");
            }
        }

        if(config == null){
            log.trace("No property file given. Using defaults instead.");
            config = new DefaultConfiguration();
            log.info("Default configuration loaded!");
        }

        WebServer server = new SocketsWebServer(config);
        server.await();

    }



    public File getPropertiesFile() {
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

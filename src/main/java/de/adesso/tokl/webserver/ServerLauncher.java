package de.adesso.tokl.webserver;

import de.adesso.tokl.webserver.sockets.ServerConfiguration;
import de.adesso.tokl.webserver.sockets.SocketsWebServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kloss on 30.04.2015.
 * <p>
 * Startup class to initialise the Server
 */
class ServerLauncher {

    public static void main(String[] args) {
        new ServerLauncher().launch();
    }

    public void launch(){

        ServerConfiguration config = null;

        try {
            checkAndCreateUserPropertiesFile();

            config = new ServerConfiguration();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        WebServer server = new SocketsWebServer(config);
        server.await();

    }

    private void checkAndCreateUserPropertiesFile() throws IOException {
        File propertiesFile = new File(ServerConfiguration.USER_PROPERTIES_PATH);
        if (!propertiesFile.exists()) {
            InputStream in = getClass().getClassLoader().getResourceAsStream("serverConfig.properties");
            Files.copy(in, Paths.get(propertiesFile.getAbsolutePath()));
        }
    }



}

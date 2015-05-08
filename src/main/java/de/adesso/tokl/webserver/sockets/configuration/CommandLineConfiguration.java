package de.adesso.tokl.webserver.sockets.configuration;

import de.adesso.tokl.webserver.sockets.SocketsWebServer;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by kloss on 08.05.2015.
 *
 * A Server configuration based on command line arguments
 */
public class CommandLineConfiguration implements ServerConfiguration {

    private final Logger logger = LogManager.getLogger(SocketsWebServer.class);
    private int serverPort;
    private String rootDirectory;

    public CommandLineConfiguration(String[] args){
        Options options = createOptions();

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Error while parsing commandline parameters.");
            logger.catching(e);
            System.exit(1);
        }

        setServerPort(cmd);
        setRootDir(cmd);

    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption("p", true, "the server port");
        options.addOption("r", true, "the root directory for the server to search for files");
        return options;
    }

    private void setRootDir(CommandLine cmd) {
        if (cmd.hasOption('r')) {
            String rootDir = cmd.getOptionValue('r');
            rootDirectory = rootDir;
        } else {
            rootDirectory = DefaultConfiguration.DEFAULT_ROOT_DIR;
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    private void setServerPort(CommandLine cmd) {
        if (cmd.hasOption('p')) {
            String portString = cmd.getOptionValue('p');
            int port = Integer.parseInt(portString);
            serverPort = port;
        } else {
            serverPort = DefaultConfiguration.DEFAULT_SERVER_PORT;
        }
    }

    public String getRootDirectory() {
        return rootDirectory;
    }
}

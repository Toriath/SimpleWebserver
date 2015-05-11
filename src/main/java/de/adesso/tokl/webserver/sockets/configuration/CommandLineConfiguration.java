package de.adesso.tokl.webserver.sockets.configuration;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.*;

/**
 * Created by kloss on 08.05.2015.
 * <p>
 * A Server configuration based on command line arguments
 */
@Log4j2
public class CommandLineConfiguration implements ServerConfiguration {



    private int serverPort;
    private String rootDirectory;

    /**
     * Constructor the CommandLineConfigurations.
     *
     * @param args the command line agrument array
     */
    public CommandLineConfiguration(final String... args) {
        Options options = createOptions();

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            throw new ConfigurationException("Could not parse the given command line arguments!");
        }

        setServerPort(cmd);
        setRootDir(cmd);

    }

    /**
     * Creates the Option model for all available command line parameters.
     *
     * @return The Option model
     */
    private Options createOptions() {
        Options options = new Options();
        options.addOption("p", true, "the server port");
        options.addOption("r", true, "the root directory for the server to search for files");
        return options;
    }

    /**
     * Checks whether the root dir was set as argument or not. Then decides to use argument or default.
     *
     * @param cmd the parsed command line
     */
    private void setRootDir(final CommandLine cmd) {
        if (cmd.hasOption('r')) {
            rootDirectory = cmd.getOptionValue('r');
        } else {
            rootDirectory = DefaultConfiguration.DEFAULT_ROOT_DIR;
        }
    }

    /**
     * Getter for the configured server port
     *
     * @return The server port
     * @see ServerConfiguration#getServerPort()
     */
    public final int getServerPort() {
        return serverPort;
    }

    /**
     * Checks whether the server port was set as argument or not. Then decides to use argument or default.
     *
     * @param cmd the parsed command line
     */
    private void setServerPort(final CommandLine cmd) {
        if (cmd.hasOption('p')) {
            String portString = cmd.getOptionValue('p');
            serverPort = Integer.parseInt(portString);
        } else {
            serverPort = DefaultConfiguration.DEFAULT_SERVER_PORT;
        }
    }

    /**
     * Getter for the configured root directory
     *
     * @return the root directory
     * @see ServerConfiguration#getRootDirectory()
     */
    public final String getRootDirectory() {
        return rootDirectory;
    }
}

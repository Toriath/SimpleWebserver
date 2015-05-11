package de.adesso.tokl.webserver.sockets.configuration;

/**
 * Created by kloss on 08.05.2015.
 * Exception thrown by configurations if they can not be loaded properly
 */
public class ConfigurationException extends RuntimeException {

    /**
     * Constructor for the ConfigurationException.
     * @param s The error Message
     */
    public ConfigurationException(String s) {
        super(s);
    }
}

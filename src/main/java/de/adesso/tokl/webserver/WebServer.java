package de.adesso.tokl.webserver;

/**
 * Created by kloss on 30.04.2015.
 *
 * Represents a Webserver to make its implementations interchangeable
 */
public interface WebServer {
    /**
     * The interface method for web severs to wait for requests.
     */
    void await();
}

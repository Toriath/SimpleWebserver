package de.adesso.tokl.webserver.sockets.http;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by kloss on 11.05.2015.
 */
@Log4j2
public class HttpHeader {

    private String contentType;
    private String serverName;

    /**
     * Creates a HttpHeader for the given content and serverName
     * @param contentUri the content this header will be used for
     * @param serverName the server that will send this header
     */
    public HttpHeader(String contentUri, String serverName) {
        try {
            Path contentPath = Paths.get(contentUri);
            this.contentType = Files.probeContentType(contentPath);
        } catch (IOException e) {
            log.catching(e);
        }
        this.serverName = serverName;
    }

    /**
     * Creates a byte array out of the httpheader message
     * @return byte array of the http header message.
     */
    public byte[] getBytes(){
        byte[] httpHeaderBytes = ("HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Date: " + new Date() + "\r\n" +
                "Server: SimpleWebserver 1.0\r\n\r\n").getBytes();
        return httpHeaderBytes;
    }

}

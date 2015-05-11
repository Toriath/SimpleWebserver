package de.adesso.tokl.webserver.sockets;

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

    public HttpHeader(String contentUri, String serverName) {
        try {
            Path contentPath = Paths.get(contentUri);
            this.contentType = Files.probeContentType(contentPath);
        } catch (IOException e) {
            log.catching(e);
        }
        this.serverName = serverName;
    }

    public byte[] getBytes(){
        byte[] httpHeaderBytes = ("HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Date: " + new Date() + "\r\n" +
                "Server: SimpleWebserver 1.0\r\n\r\n").getBytes();
        return httpHeaderBytes;
    }

}

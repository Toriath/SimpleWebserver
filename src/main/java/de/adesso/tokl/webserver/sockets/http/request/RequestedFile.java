package de.adesso.tokl.webserver.sockets.http.request;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kloss on 13.05.2015.
 * A File that was requested by a client.
 */
@Log4j2
public class RequestedFile {

    private File requestedFile;

    /**
     * Creates a File wich is being requested by a client
     *
     * @param baseDir  the rootDirectory of the Server
     * @param fileName The name of the requested file
     */
    public RequestedFile(String baseDir, String fileName) {
        File file = new File(baseDir, fileName);
        requestedFile = file;

    }

    /**
     * Checks the content type of the requested file
     *
     * @return the content type of the requested file as String
     */
    public String getContentType() {
        URI requestedUri = requestedFile.toURI();
        Path requestedPath = Paths.get(requestedUri);
        String contentType = null;
        try {
            contentType = Files.probeContentType(requestedPath);
        } catch (IOException e) {
            log.catching(e);
            log.error("Could not probe the content type of the requested file");
        }
        if (contentType == null) {
            contentType = "text/plain";
        }
        return contentType;
    }

    /**
     * Gets the file input stream for the file
     *
     * @return the file input stream
     */
    private FileInputStream getFileInputStream() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(requestedFile);
        } catch (FileNotFoundException e) {
            log.catching(e);
            log.error("Could not find the requested file");
        }
        return fileInputStream;
    }

    /**
     * Reads the requested file and converts it to an array of bytes
     *
     * @param requestedFileBytes the byte array to fill
     */
    private void readFile(byte[] requestedFileBytes) {
        FileInputStream fileInputStream = getFileInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        try {
            bufferedInputStream.read(requestedFileBytes, 0, requestedFileBytes.length);
        } catch (IOException e) {
            log.catching(e);
            log.error("Could not read the requested file");
        }
    }

    /**
     * Returns the bytes of the file as array.
     *
     * @return a byte array containing the bytes of the file.
     */
    public byte[] getBytes() {
        byte[] requestedFileBytes = new byte[(int) requestedFile.length()];
        readFile(requestedFileBytes);
        return requestedFileBytes;
    }

    /**
     * Checks if a file is a directory and redirects to an index.html if true
     */
    private void redirectToIndex() {
        if (requestedFile.isDirectory()) {
            requestedFile = new File(requestedFile.getPath() + File.separator + "index.html");
        }

    }

    /**
     * Checks whether or not the file does exist in the file system
     *
     * @return true if the file exists, otherwise false.
     */
    public boolean exists() {
        return requestedFile.exists();
    }
}

package de.adesso.tokl.webserver.sockets.http.request;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kloss on 13.05.2015.
 */
@Log4j2
public class RequestedFile {

    private File requestedFile;

    //TODO: Refactor this class to the max


    public RequestedFile(String baseDir, String fileName){
        File file = new File(baseDir, fileName);
        requestedFile = file;

    }

    public String getContentType(){
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

    public byte[] getBytes() {
        byte[] requestedFileBytes = new byte[(int) requestedFile.length()];
        readFile(requestedFileBytes);
        return requestedFileBytes;
    }

    /**
     * Checks if a file is a directory and redirects to an index.html if true
     *
     * @return the redirected file. If the original file was not a file it is returned without being changed.
     */
    private void redirectToIndex() {
        if (requestedFile.isDirectory()) {
            requestedFile = new File(requestedFile.getPath() + File.separator + "index.html");
        }

    }

    public boolean exists(){
        return requestedFile.exists();
    }
}

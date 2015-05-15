package de.adesso.tokl.webserver.sockets.http.response;

import de.adesso.tokl.webserver.sockets.http.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by kloss on 13.05.2015.
 * <p>
 * Abstract class to respresent a http response providing hooks for its subclasses what to send as response
 */
@Log4j2
public abstract class HttpResponse {

    private OutputStream output;

    /**
     * Constructor for a HttpResponse.
     * @param socket the socket to respond
     */
    public HttpResponse(Socket socket) {
        try {
            this.output = socket.getOutputStream();
        } catch (IOException e) {
            log.catching(e);
            throw new InternalServerErrorException("Could not get the outputsteam from the socket", e);
        }
    }

    /**
     * Sends the response to the socket
     */
    public final void sendResponse() {
        sendStatusCode();
        sendHeaderInformation();
        sendData();
    }

    /**
     * Sends the data part of the response to the socket
     */
    private void sendData() {
        try {
            sendBytes(getDataBytes());
        } catch (IOException e) {
            log.catching(e);
            log.error("Error while sending response data");
        }
    }

    /**
     * Sends the header information to the socket.
     */
    private void sendHeaderInformation() {
        try {
            sendBytes(getHeaderBytes());
        } catch (IOException e) {
            log.catching(e);
            log.error("Error while sending response header");
        }
    }

    /**
     * Sends the http status code to the socket.
     */
    private void sendStatusCode() {
        try {
            sendBytes(getStatusCodeBytes());
        } catch (IOException e) {
            log.catching(e);
            log.error("Error while sending response status code");
        }
    }

    /**
     * Sends the given byte array to the socket.
     * @param dataBytes the bytes to send
     * @throws IOException in case the bytes can not be written to the output stream.
     */
    private void sendBytes(byte[] dataBytes) throws IOException {
        output.write(dataBytes);
        output.flush();
    }

    /**
     * Hook for the subclasses to decide what data to send
     * @return the bytes of the data
     */
    protected abstract byte[] getDataBytes();

    /**
     * Hook for the subclasses to decide what header to send
     * @return the bytes of the header
     */
    protected abstract byte[] getHeaderBytes();

    /**
     * Hook for the subclasses to decide what status code to send
     * @return the bytes of the status code
     */
    protected abstract byte[] getStatusCodeBytes();
}

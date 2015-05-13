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


    public HttpResponse(Socket socket) {
        try {
            this.output = socket.getOutputStream();
        } catch (IOException e) {
            log.catching(e);
            throw new InternalServerErrorException("Could not get the outputsteam from the socket", e);
        }
    }

    public final void sendResponse() {
        sendStatusCode();
        sendHeaderInformation();
        sendData();
    }

    private void sendData() {
        try {
            sendBytes(getDataBytes());
        } catch (IOException e) {
            log.catching(e);
            log.error("Error while sending response data");
        }
    }


    private void sendHeaderInformation() {
        try {
            sendBytes(getHeaderBytes());
        } catch (IOException e) {
            log.catching(e);
            log.error("Error while sending response header");
        }
    }

    private void sendStatusCode() {
        try {
            sendBytes(getStatusCodeBytes());
        } catch (IOException e) {
            log.catching(e);
            log.error("Error while sending response status code");
        }
    }

    private void sendBytes(byte[] dataBytes) throws IOException {
        output.write(dataBytes);
        output.flush();
    }

    protected abstract byte[] getDataBytes();

    protected abstract byte[] getHeaderBytes();

    protected abstract byte[] getStatusCodeBytes();
}

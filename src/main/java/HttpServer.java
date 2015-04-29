import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kloss on 29.04.2015.
 *
 * A Simple HttpServer that returns Static HTML Pages and Images on HTTP requests
 */
public class HttpServer {

    private static final String ROOT_DIRECTORY = System.getProperty("user.dir") + File.separator  + "SimpleWebserver"; //TODO Make this configurable
    private boolean running;

    public void await() {
        running = true; // http://www.onjava.com/pub/a/onjava/2003/04/23/java_webserver.html?page=2
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket =  new ServerSocket(port, 1,
                    InetAddress.getByName("127.0.0.1"));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Loop waiting for a request
        while (running) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // create Request object and parse
                Request request = new Request(input);
                request.parse();

                // create Response object
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                // Close the socket
                socket.close();


            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}

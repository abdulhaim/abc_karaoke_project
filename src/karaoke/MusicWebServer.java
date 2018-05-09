package karaoke;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

/**
 * @author Bibek Kumar Pandit
 *
 * HTTP web server for Music.
 */
public class MusicWebServer {
    /*
     * AF(server) = server that can handle connect and play requests 
     * RI: true
     * Safety from Rep Exposure: fields are private and final, mutable references are never exposed
     * Thread Safety:
     * Music is immutable, no threads can modify it
     * At most one thread created for every person that listens 
     * server is thredsafe because it's private and final 
     * 
     */
    
    //fields
    private static final int VALID_HTTP_CODE = 200;
    private static final int ERR_HTTP_CODE = 404;
    private final HttpServer server;
    
    /**
     * Make a new web server for Music that listens for connections on port.
     * 
     * @param port server port number
     * @throws IOException if there is an error starting the musicwebserver
     */
    public MusicWebServer(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
    }
    
    /**
     * @return port number the server is listening on
     */
    public int port() {
        return server.getAddress().getPort();
    }
    
    /**
     * Start this server in a new background thread.
     */
    public void start() {
        
    }
    
    /**
     * Stop this server. Once stopped, this server cannot be restarted.
     */
    public void stop() {
        
    }
    
    // Implement private handle requests. We might have to implement handle request for something like connect and play.
    
    
}

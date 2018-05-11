package karaoke;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
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
     * server is threadsafe because it's private and final 
     * 
     */
    
    //fields

    private final Set<Double> players = new HashSet<Double>();
    private final HttpServer server;
    
    /**
     * Make a new web server for Music that listens for connections on port.
     * 
     * @param port server port number
     * @throws IOException if there is an error starting the musicwebserver
     */
    public MusicWebServer(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        LogFilter log = new LogFilter();
        HeadersFilter headers = new HeadersFilter();
        // allow requests from web pages hosted anywhere
        headers.add("Access-Control-Allow-Origin", "*");
        // all responses will be plain-text UTF-8
        headers.add("Content-Type", "text/plain; charset=utf-8");
        List<Filter> filterList = Arrays.asList(log, headers);
        HttpContext watch = server.createContext("/connect/", this::handleConnect);
        watch.getFilters().addAll(filterList);
        HttpContext look = server.createContext("/play/", this::handlePlay);
        look.getFilters().addAll(filterList);
        checkRep();
    }
    /**
     * checks the rep
     */
    private void checkRep() {
        InetSocketAddress serverAddress = server.getAddress();
        assert serverAddress != null;
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
        System.err.println("Server will listen on " + server.getAddress());
        server.start();
        
    }
    
    /**
     * Stop this server. Once stopped, this server cannot be restarted.
     */
    public void stop() {
        System.err.println("Server will stop");
        server.stop(0);
    }
    
    /**
     * 
     * @param exchange
     * @throws IOException
     */
    private void handleConnect (HttpExchange exchange) throws IOException  {
        String startPath = exchange.getHttpContext().getPath();
        String  getPath = exchange.getRequestURI().getPath();
        String path = getPath.substring(startPath.length());
        players.add(players.size() + 1.0);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        String response = "connected... now waiting for play";
        OutputStream body = exchange.getResponseBody();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
        out.println(response);
        exchange.close(); 
    }
    
    /**
     * 
     * @param exchange
     * @throws IOException
     */
    private void handlePlay(HttpExchange exchange) throws IOException {
        
    }
    
    
}

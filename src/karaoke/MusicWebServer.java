package karaoke;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.sound.MusicLanguage;
import karaoke.sound.SoundPlayback;




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

    private final Set<String> voices = new HashSet<String>();
    private final HttpServer server;
    private boolean play = false;
    private final String filePath;
    private final List<PrintWriter> outList = new ArrayList<PrintWriter>();
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private boolean done = false;
    /**
     * Make a new web server for Music that listens for connections on port.
     * 
     * @param port server port number
     * @param filePath the path to the abc file
     * @throws IOException if there is an error starting the musicwebserver
     * 
     */
    public MusicWebServer(int port, String filePath) throws IOException {
        System.out.println("hereeee");

        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.filePath = filePath;
       
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/stream", exchange -> {
            try {
                handleStream(exchange);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        System.out.println("hereeee");
        server.createContext("/play", exchange -> {
            try {
                System.out.println("inside");
                handlePlay(exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            } catch (UnableToParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
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
     * @throws InterruptedException 
     */
    private void handleStream (HttpExchange exchange) throws IOException, InterruptedException  {
        //String startPath = exchange.getHttpContext().getPath();
        String  path = exchange.getRequestURI().getPath();
        System.err.println("received request " + path); //TODO remove when done 
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream body = exchange.getResponseBody();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
        outList.add(out);
        final int enoughBytesToStartStreaming = 2048;
        for (int i = 0; i < enoughBytesToStartStreaming; ++i) {
            out.print(' ');
        }
        while (!play) {
            synchronized (server) {
                server.wait();
                }
        }
        try {
            this.displayLyrics();
        }
        finally {
            synchronized(queue) {
                queue.wait();
            }
            exchange.close();
        }
    }
    
    /**
     * 
     * @param exchange
     * @throws IOException
     * @throws UnableToParseException 
     * @throws InvalidMidiDataException 
     * @throws MidiUnavailableException 
     * @throws InterruptedException 
     */
    private void handlePlay(HttpExchange exchange) throws InterruptedException, IOException, MidiUnavailableException, InvalidMidiDataException, UnableToParseException {
        play = true;
        synchronized (server) {
            server.notifyAll();
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        String response = "Playing now, lyrics streaming has begun"; 
        System.out.println("here!");
        System.out.println(MusicLanguage.parse(filePath));

 
 
        try {
            System.out.println("here!!");

        SoundPlayback.play(MusicLanguage.parse(filePath), queue);
        }
        catch (UnableToParseException e) {
             System.out.println("exception");
        }
        
        OutputStream body = exchange.getResponseBody();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
        out.println(response);
        exchange.close(); 
    }
    
    private void displayLyrics() throws InterruptedException {
        while (!queue.isEmpty()) {
            String line = queue.take();
            for (PrintWriter out: outList) {
                if (line != "$" ) {
                    out.print(line);
                }
                else {
                    done = true;
                    synchronized (queue) {
                        queue.notifyAll();
                    }
                }
            }
        }
        
    }
    
    
    
}

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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

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
    /**
     * Make a new web server for Music that listens for connections on port.
     * 
     * @param port server port number
     * @throws IOException if there is an error starting the musicwebserver
     */
    public MusicWebServer(int port, String filePath) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.filePath = filePath;
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/stream", this::handleStream);
        server.createContext("/play", exchange -> {
            try {
                handlePlay(exchange);
            } catch (MidiUnavailableException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvalidMidiDataException e) {
                // TODO Auto-generated catch block
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
     */
    private void handleStream (HttpExchange exchange) throws IOException  {
        //String startPath = exchange.getHttpContext().getPath();
        String  path = exchange.getRequestURI().getPath();
        System.err.println("received request " + path); //TODO remove when done 
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream body = exchange.getResponseBody();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
        try {
            // IMPORTANT: some web browsers don't start displaying a page until at least 2K bytes
            // have been received.  So we'll send a line containing 2K spaces first.
            final int enoughBytesToStartStreaming = 2048;
            for (int i = 0; i < enoughBytesToStartStreaming; ++i) {
                out.print(' ');
            }
            out.println();
            if (!play) {
                String response = "Streaming will begin once play has been triggered.";
                out.print(response);
                }
            else {
                String response = "Lyrics played line by line";
                out.println(response);
                }
        }
        finally {
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
     */
    private void handlePlay(HttpExchange exchange) throws IOException, MidiUnavailableException, InvalidMidiDataException, UnableToParseException {
        play = true;
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        String response = "Playing now, lyrics streaming has begun"; 
        OutputStream body = exchange.getResponseBody();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
        out.println(response);
        String file = readFile(filePath);
        SoundPlayback.play(MusicLanguage.parse(file));
        exchange.close(); 
        
    }
    private static String readFile(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
     
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("File either not readable or does not exist.");
        }
        return contentBuilder.toString();
    }
    
}

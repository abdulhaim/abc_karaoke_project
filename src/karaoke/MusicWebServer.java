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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.sound.AbcTune;
import karaoke.sound.MusicLanguage;
import karaoke.sound.SoundPlayback;
import karaoke.sound.Voices;

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

    private final List<String> voices;
    private final HttpServer server;
    private boolean play = false;
    private final String filePath;
    private final Map<String, List<PrintWriter>> outMap = new HashMap<String, List<PrintWriter>>();
    private boolean done = false;
    private Object lock = new Object();
    private Map<String, BlockingQueue<String>> voiceMap = new HashMap<String, BlockingQueue<String>>();

    /**
     * Make a new web server for Music that listens for connections on port.
     * 
     * @param port server port number
     * @param filePath the path to the abc file
     * @throws IOException if there is an error starting the musicwebserver
     * 
     */
    public MusicWebServer(int port, String filePath, List<String> voices) throws IOException {

        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.filePath = filePath;
        this.voices = voices;
        for (String voice:voices) {
            voiceMap.put(voice, new LinkedBlockingQueue<String>());
            outMap.put(voice, new ArrayList<PrintWriter>());
        }
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/stream", exchange -> {
            try {
                handleStream(exchange);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        server.createContext("/play", exchange -> {
            try {
                handlePlay(exchange);
            } catch (InterruptedException | MidiUnavailableException | InvalidMidiDataException | UnableToParseException e) {
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
        String startPath = exchange.getHttpContext().getPath();
        String  getPath = exchange.getRequestURI().getPath();
        String path = getPath.substring(startPath.length());
        String[] arguments = path.split("/");
        String voice = arguments[0].trim();
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream body = exchange.getResponseBody();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
        final int enoughBytesToStartStreaming = 2048;
        for (int i = 0; i < enoughBytesToStartStreaming; ++i) {
            out.print(' ');
        }
        
        outMap.get(voice).add(out);
        
        while (!play) {
            synchronized (lock) {
                lock.wait();
                }
        }
        try {
            out.println();
            this.displayLyrics(voice);
        } finally
         {
            synchronized(this) {
                this.wait();
                System.out.print("waiting for lyrics to end");
            } 
            exchange.close(); }
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

        synchronized (lock) {
            lock.notifyAll();
        }

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        String response = "Playing now, lyrics streaming has begun"; 

        OutputStream body = exchange.getResponseBody();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(body, UTF_8), true);
        final int enoughBytesToStartStreaming = 2048;

        for (int i = 0; i < enoughBytesToStartStreaming; ++i) {
            out.print(' ');
        }
        out.println(response);
        //out.println(MusicLanguage.parse(readFile(filePath)));
        AbcTune tune = MusicLanguage.parse(readFile(filePath));
        SoundPlayback.play(tune.getMusic(), voiceMap ,Integer.parseInt(tune.getTempo())); 

        exchange.close(); 
        
        

    }
    
    private void displayLyrics(String voice) throws InterruptedException {
        while (!done) {
            String line =  voiceMap.get(voice).take();
            for (PrintWriter out: outMap.get(voice)) {
                if (!line.equals("$") ) {
                    out.println(line);
                }
                else {
                    done = true;
                    synchronized (this) {
                        this.notifyAll();
                    }
                }
            }
        }
        
    }
    
    
    
    private static String readFile(String filePath){
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
            throw new IllegalArgumentException("File either not readable or does not exist. \nPlease check the file path and try again");
        }
        return contentBuilder.toString().trim();
    }
    
}
    

package karaoke;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import examples.StreamingExample;

public class ServerMain {
    /**
     * TAKEN FROM STREAMING EXAMPLE
     * Web server that demonstrates several ways to stream text to a web browser.
     *     
     * @param args not used
     * @throws IOException if network failure
     */
    public static void main(String[] args) throws IOException {
        
        // make a web server
        final int serverPort = 4567;
        final HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        
        // handle concurrent requests with multiple threads
        server.setExecutor(Executors.newCachedThreadPool());

        // register handlers
        server.createContext("/textStream", ServerMain::textStream);
        //server.createContext("/htmlStream", StreamingExample::htmlStream);
        //server.createContext("/htmlWaitReload", StreamingExample::htmlWaitReload);

        // start the server
        server.start();
        System.out.println("server running, browse to one of these URLs:");
        System.out.println("http://localhost:4567/textStream");
        System.out.println("http://localhost:4567/htmlStream");
        System.out.println("http://localhost:4567/htmlStream/autoscroll");
        System.out.println("http://localhost:4567/htmlWaitReload");
    }
    /**
     * This handler sends a plain text stream to the web browser,
     * one line at a time, pausing briefly between each line.
     * Returns after the entire stream has been sent.
     * 
     * @param exchange request/reply object
     * @throws IOException if network problem
     */
    private static void textStream(HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();
        System.err.println("received request " + path);

        // plain text response
        exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");

        // must call sendResponseHeaders() before calling getResponseBody()
        final int successCode = 200;
        final int lengthNotKnownYet = 0;
        exchange.sendResponseHeaders(successCode, lengthNotKnownYet);

        // get output stream to write to web browser
        final boolean autoflushOnPrintln = true;
        PrintWriter out = new PrintWriter(
                              new OutputStreamWriter(
                                  exchange.getResponseBody(), 
                                  StandardCharsets.UTF_8), 
                              autoflushOnPrintln);
        
        try {
            // IMPORTANT: some web browsers don't start displaying a page until at least 2K bytes
            // have been received.  So we'll send a line containing 2K spaces first.
            final int enoughBytesToStartStreaming = 2048;
            for (int i = 0; i < enoughBytesToStartStreaming; ++i) {
                out.print(' ');
            }
            out.println(); // also flushes
            
            final int numberOfLinesToSend = 100;
            final int millisecondsBetweenLines = 200;
            for (int i = 0; i < numberOfLinesToSend; ++i) {
                
                // print a line of text
                out.println(System.currentTimeMillis()); // also flushes

                // wait a bit
                try {
                    Thread.sleep(millisecondsBetweenLines);
                } catch (InterruptedException e) {
                    return;
                }
            }
            
        } finally {
            exchange.close();
        }
        System.err.println("done streaming request");
    }

}

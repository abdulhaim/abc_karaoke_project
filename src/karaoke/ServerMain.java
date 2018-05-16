package karaoke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import examples.StreamingExample;

public class ServerMain {
    /**
     * Web server uses plain text stream
     *     
     * @param args 
     * @throws IOException if network failure
     */

    public static void main(String[] args) throws IOException {
        
        // make a web server
        final int serverPort = 4567;
        List<String> voiceList = getVoicesFromFile(args[0]);
        final MusicWebServer server = new MusicWebServer(serverPort, args[0], voiceList);

        String header = getHeaderFromFile(args[0]);
        // start the server
        server.start();
        System.out.println(header);
        InetAddress inetAddress = InetAddress.getLocalHost(); 
        if (voiceList.size() == 1) {
            System.out.println("Instructions: \nTo begin play browse to: \n    http://" + inetAddress.getHostAddress() + ":4567/play \nTo view lyrics browse to \n    http://" + inetAddress.getHostAddress() + ":4567/stream/" +voiceList.get(0) ); 
        }
        else {
            System.out.println("Instructions: \nTo begin play browse to: \n    http://" + inetAddress.getHostAddress() + ":4567/play \n");
            for (String voice: voiceList) {
                System.out.println("To view lyrics for "  + voice+ " browse to \n http://" + inetAddress.getHostAddress() + ":4567/stream/" + voice + "\n" );
            }
            
        }
        
    }

    /**
     * gets the Title and Composer from the header of the file
     * @param filePath the path to the abc file
     * @return A string representing the title and composer
     */
    private static String getHeaderFromFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
     
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                if (sCurrentLine.startsWith("T:")) {
                    contentBuilder.append(sCurrentLine).append("\n");
                }
                else if (sCurrentLine.startsWith("C:")) {
                    contentBuilder.append(sCurrentLine).append("\n");

                }
            }
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("File either not readable or does not exist. \nPlease check the file path and try again");
        }
        return contentBuilder.toString().trim();
    }
    private static List<String> getVoicesFromFile(String filePath){
        List<String> voices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                if (sCurrentLine.startsWith("V:")) {
                    if (! voices.contains(sCurrentLine.substring(2).trim())) {
                    voices.add(sCurrentLine.substring(2).trim());
                    }
                } 
            }
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("File either not readable or does not exist. \nPlease check the file path and try again");
        }
        if (voices.size() >1 ) {
            return voices;
        }
        else {
            voices.add("OneVoice");
            return voices;
            
        }
    }
}


package karaoke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;



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
        final MusicWebServer server = new MusicWebServer(serverPort, args[0]);

        String header = getHeaderFromFile(args[0]);
        // start the server
        server.start();
        System.out.println(header);
        InetAddress inetAddress = InetAddress.getLocalHost();
        
        System.out.println("Instructions: \nTo begin play browse to: \n    http://" + inetAddress.getHostAddress() + ":4567/play \nTo view lyrics browse to \n    http://" + inetAddress.getHostAddress() + ":4567/stream" );
    }

    /**
     * gets the Title and Composer from the header of the file
     * @param filePath the path to the abc file
     * @return A string representing the title and composer
     */
    private static String getHeaderFromFile(String filePath){
        System.out.println(filePath);
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
}

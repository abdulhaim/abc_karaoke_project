package karaoke.sound;


public class WebServerTest {
    
    /*
     * Partitions:
     * 
     * Valid URL, Invalid URL 
     * Valid Connection, Invalid Connection
     */
    
    /*
     * *******************************************************************
     * Manual tests:
     * *******************************************************************
     * 
     * 
     * 
     * Test 1:
     * Open one command prompt and browse to the ps4 folder.
     * For the first command prompt window, paste the following:
     *      java -cp bin karaoke.ServerMain web 8080 3 3 1F308 1F984
     *      
     * Open two windows.
     * Connect one person to listen 
     *      http://localhost:8080/connect/1
     *      
     * For each of these windows, display the following:
     *      the title and composer (if any) from the header of the abc file should be the first line printed
     *      instructions about how to view lyrics streams with a web browser (including a complete URL with IP address and port number, e.g. http://128.30.93.234:7283)
     *      instructions about how to start music playback
     * 
     * To listen to a song in the first window, change the command in the first window to the following:
     *      http://localhost:8080/play/1
     *
     * assert that you can listen to the song and view the lyrics on the screen as the song plays 
     *      
     * Test 2:
     * Open one command prompt and browse to the abdulhai-myraa-bibek folder/
     * For the first command prompt window, paste the following:
     *      java -cp bin karaoke.ServerMain web 8080 3 3 1F308 1F984
     *      
     *      
     * Open two windows.
     * Connect one person to listen 
     *      http://localhost:8080/connect/1
     * Connect second person to listen
     *      http://localhost:8080/connect/2
     * 
     * For each of these windows, display the following:
     *      the title and composer (if any) from the header of the abc file should be the first line printed
     *      instructions about how to view lyrics streams with a web browser (including a complete URL with IP address and port number, e.g. http://128.30.93.234:7283)
     *      instructions about how to start music playback
     * 
     * To listen to a song in the first window, change the command in the first window to the following:
     *      http://localhost:8080/play/2
     *      
     * assert that you can listen to the song and view the lyrics on the screen as the song plays on both ends 
     * 

     *      
     */

}

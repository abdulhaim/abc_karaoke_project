package karaoke.sound;


public class WebServerTest {
    
    /*
     * Partitions:
     * 
     * No lyrics, 1 voice, multiple voices 
     * one streamer, more than 1 streamer 
     * Valid Connection, Invalid Connection
     */
    
    /*
     * *******************************************************************
     * Manual tests:
     * *******************************************************************
     * 
     * 
     * 
     * Test 1, streaming for one person without lyrics:
     * Open one command prompt and browse to the ps4 folder.
     * For the first command prompt window, paste the following:
     *      java -cp bin:lib/parserlib.jar karaoke.ServerMain FILEPATH to piece1.abc
     *      
     *      
     * The command line should display the following:
     *      the title and composer (if any) from the header of the abc file should be the first line printed
     *      instructions about how to view lyrics streams with a web browser (including a complete URL with IP address and port number, e.g. http://128.30.93.234:7283)
     *      instructions about how to start music playback   
     *      
     * Open two windows.
     * Connect one person to listen 
     *      to the streaming link displayed
     *      to the play link displayed
     *      
     * 
     *
     * assert that you can listen to the song 
     *      
     * Test 2, streaming for 2+ people without lyrics, connecting before and after playback has started:
     * Open one command prompt and browse to the abdulhai-myraa-bibek folder/
     * For the first command prompt window, paste the following:
     *      java -cp bin:lib/parserlib.jar karaoke.ServerMain FILEPATH to piece2.abc
     *      
     *The command line should display the following:
     *      the title and composer (if any) from the header of the abc file should be the first line printed
     *      instructions about how to view lyrics streams with a web browser (including a complete URL with IP address and port number, e.g. http://128.30.93.234:7283)
     *      instructions about how to start music playback   
     * 
     *      
     * Open two windows
     *      Connect 2 tabs to the streaming link
     *      Begin Playback
     *      On another device, open the streaming link
     
     * 
     * assert that you can listen to the song and all windows are loaded properly (no connect errors 404 errors, etc)
     * 
     * 
     * Test 3: Streaming for one person with lyrics:
     * Open one command prompt and browse to the abdulhai-myraa-bibek folder/
     * For the first command prompt window, paste the following:
     *      java -cp bin:lib/parserlib.jar karaoke.ServerMain FILEPATH TO piece3.abc
     * 
     *The command line should display the following:
     *      the title and composer (if any) from the header of the abc file should be the first line printed
     *      instructions about how to view lyrics streams with a web browser (including a complete URL with IP address and port number, e.g. http://128.30.93.234:7283)
     *      instructions about how to start music playback   
     *
     * Open two tabs
     *      Connect 1 tab to the streaming link
     *      Connect 1 tab to the play link 
     *           
     * 
     * assert that you can listen to the song and all windows are loaded properly (no connect errors 404 errors, etc),
     *  and that you can see the lyrics exphasized at the syllables that they should be
     *  
     * Test 4: Streaming for 2+ person with lyrics with only one voice:
     * Open one command prompt and browse to the abdulhai-myraa-bibek folder/
     * For the first command prompt window, paste the following:
     *      java -cp bin:lib/parserlib.jar karaoke.ServerMain FILEPATH TO piece3.abc
     * 
     *The command line should display the following:
     *      the title and composer (if any) from the header of the abc file should be the first line printed
     *      instructions about how to view lyrics streams with a web browser (including a complete URL with IP address and port number, e.g. http://128.30.93.234:7283)
     *      instructions about how to start music playback   
     *
     * Open 4 tabs
     *      Connect 1 tab to the streaming link
     *      Connect 1 tab to the streaming link
     *      Connect 1 tab to the play link 
     *      Connect 1 tab to the streaming link 
     *           
     * 
     * assert that you can listen to the song and all windows are loaded properly (no connect errors 404 errors, etc),
     *  and that you can see the lyrics exphasized at the syllables that they should be at all tabs
     *  and that when you open the streaming link after you start playing it starts the lyrics at where the song is at,
     *  not from the beginning
     * 
     * 
     * Test 5: Streaming for 1 person with lyrics with multiple voices :
     * Open one command prompt and browse to the abdulhai-myraa-bibek folder/
     * For the first command prompt window, paste the following:
     *      java -cp bin:lib/parserlib.jar karaoke.ServerMain FILEPATH TO payphone.abc
     * 
     *The command line should display the following:
     *      the title and composer (if any) from the header of the abc file should be the first line printed
     *      instructions about how to view lyrics streams with a web browser (including a complete URL with IP address and port number, e.g. http://128.30.93.234:7283)
     *      instructions about how to start music playback   
     *
     * Open 3 tabs
     *      Connect 1 tab to the streaming link for voice 1
     *      Connect 1 tab to the streaming link for voice 2
     *      Connect 1 tab to the play link 
     *           
     * 
     * assert that you can listen to the song and all windows are loaded properly (no connect errors 404 errors, etc),
     *  and that you can see the lyrics emphasized at the syllables that they should be at all tabs
     * 
     * Test 6: Streaming for multiple people with lyrics with multiple voices :
     * Open one command prompt and browse to the abdulhai-myraa-bibek folder/
     * For the first command prompt window, paste the following:
     *      java -cp bin:lib/parserlib.jar karaoke.ServerMain FILEPATH TO payphone.abc
     * 
     *The command line should display the following:
     *      the title and composer (if any) from the header of the abc file should be the first line printed
     *      instructions about how to view lyrics streams with a web browser (including a complete URL with IP address and port number, e.g. http://128.30.93.234:7283)
     *      instructions about how to start music playback   
     *
     * Open 5 tabs
     *      Connect 1 tab to the streaming link for voice 1
     *      Connect 1 tab to the streaming link for voice 2
     *      Connect 1 tab to the play link 
     *      Connect 1 tab to the streaming link for voice 1
     *      Connect 1 tab to the streaming link for voice 2
     *           
     * 
     * assert that you can listen to the song and all windows are loaded properly (no connect errors 404 errors, etc),
     *  and that you can see the lyrics emphasized at the syllables that they should be at all tabs
     *  
     *      
     */

}

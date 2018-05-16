package karaoke.sound;
<<<<<<< Updated upstream

import static org.junit.Assert.assertEquals;
=======
>>>>>>> Stashed changes

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import edu.mit.eecs.parserlib.UnableToParseException;

/**
 * @author Bibek Kumar Pandit
 * 
 * Implements tests for MusicLanguage.
 * 
 */
public class MusicLanguageTest {
    
    /* Testing Strategy:
     * 
     * Partition on inputs:
     * - 
     * 
     */
    
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
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void testPiece1() {
        //TODO
    }

   /* @Test
    public void testPiece1() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException{
        String path = "sample-abc/piece1.abc";
        String musicFile = readFile(path);
        Music parsedMusic = MusicLanguage.parse(musicFile);
        System.out.println("HEHHHHHHHHHHHHHHH "+parsedMusic.getDuration());
        System.out.println(parsedMusic);
        assertEquals(16.0, parsedMusic.getDuration(), 0.001);
<<<<<<< HEAD
=======
   @Test
    public void testPiece1() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        String path = "sample-abc/piece1.abc";
        String musicFile = readFile(path);
        System.out.println(musicFile);
        AbcTune parsedMusic = MusicLanguage.parse(musicFile);        
        Music p = parsedMusic.getCompleteMusic();
        assertEquals(16.0, p.getDuration(), 1);
>>>>>>> 957f4a3e39aa418980d5dd84ed9e02e9a9f452ad
=======
>>>>>>> parent of 69e09e9... adding repeat with multiple lines
        SequencePlayer player = new MidiSequencePlayer();
        //BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        p.play(player, 0);
        player.play();
    }
<<<<<<< HEAD
     
    @Test
=======
    
>>>>>>> 180c91f1d3f6c20b2ed135e8661cbc48b28c0389
    public void testPiece2() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException{
        String path = "sample-abc/piece2.abc";
        String musicFile = readFile(path);
        AbcTune parsedMusic = MusicLanguage.parse(musicFile);
        Music p = parsedMusic.getCompleteMusic();
        assertEquals(24.0, p.getDuration(), 0.001);
        SequencePlayer player = new MidiSequencePlayer();
        //BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        p.play(player, 0);
        player.play();
    }
    
    public void testPiece3() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException{
        String path = "sample-abc/piece3.abc";
        String musicFile = readFile(path);
        AbcTune parsedMusic = MusicLanguage.parse(musicFile);
        Music p = parsedMusic.getCompleteMusic();

        assertEquals(24.0, p.getDuration(), 0.001);
        SequencePlayer player = new MidiSequencePlayer();
        //BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        p.play(player, 0);
        player.play();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUnavailablePiece() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException{
        String path = "sample-abc/pieceA.abc";
        readFile(path);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testParseException() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException{
        String path = "sample-abc/piecec.abc";
        String musicFile = readFile(path);
        MusicLanguage.parse(musicFile);
    }*/
   
    
   
}


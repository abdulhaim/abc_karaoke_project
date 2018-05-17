package karaoke.sound;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import edu.mit.eecs.parserlib.UnableToParseException;

/**
 * Test functionalities of MusicLanguage for parsing lyrics 
 * @category no_didit
 */

public class MusicLanguageTest {
    
    /*
     * Partitions:
     * Covering all grammar nonterminals:
     *      ABCTUNE, ABCHEADER, FIELDNUMBER, FIELDTITLE, OTHERFIELDS, FIELDCOMPOSER, 
     *      TEXT,WHITESPACE, MIDDLEOFBODYFIELD,LYRICTEXT,COMMENTTEXT,FIELDDEFAULTLENGTH, FIELDMETER, 
     *      FIELDTEMPO, FIELDVOICE, FIELDKEY, KEY, KEYNOTE,MODEMINOR,METER, METERFRACTION,ABCBODY, 
     *      ABCLINE, NOTEELEMENT, NOTE,PITCH,OCTAVE, NOTELENGTH, NOTELENGTHSTRICT, ACCIDENTAL,BASENOTE,
     *      RESTELEMENT, TUPLETELEMENT, TUPLETSPEC,CHORD, BARLINE, NTHREPEAT,LYRIC, LYRICALELEMENT, 
     *      BACKSLASHHYPHEN,COMMENT, ENDOFLINE, DIGIT,NEWLINE,SPACEORTAB
     * Exception when parsing, no exception when parsing
     */
    
    //Covers case of getting the title, tempo, composer, index number, meter number and key signature of a piece
    //Covers case of having lyrics in file
    @Test
    public void testSimpleLyricsOneVoice() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/star_spangled_banner.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
        assertEquals("Expect Correct Title","The Star Spangled Banner",musicPiece.getTitle());
        assertEquals("Expect Correct Tempo","100",musicPiece.getTempo());
        assertEquals("Expect Correct Composer","Unknown Composer",musicPiece.getComposer());
        assertEquals("Expect Correct Index Number",2,musicPiece.getIndexNumber());
        assertEquals("Expect Correct Meter Number","3/4",musicPiece.getMeter());
        assertEquals("Expect Correct Key Signature",new ArrayList<String>(),musicPiece.getKeySignature());


    }
    //Covers partition of including Note and Chord
    @Test
    public void testSimpleMusic() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/piece1.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
        String expected = "V: [C1.0 C1.0 C0.75 D0.25 E1.0 |E0.75 D0.25 E0.75 F0.25 G2.0 |(3.0C'0.335C'0.335C'0.335 (3.0G0.335G0.335G0.335 (3.0E0.335E0.335E0.335 (3.0C0.335C0.335C0.335 |G0.75 F0.25 E0.75 D0.25 C2.0 |]\r";
        assertEquals("Expect correct length of string representation",expected.length(), musicPiece.getMusic().toString().length());
    

    }
    
    //Covers case of parsing multiple Voices 
    //Covers case of Rests 
    //Covers case of having no parser exceptions 
    @Test
    public void testMultipleVoices() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/despacito.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
        assertEquals("Expect correct length of string representation",4953, musicPiece.getMusic().toString().length());


        
    }
    //Covers case of having multiple chords 
    @Test
    public void testComplicatedChordAccidental() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/little_night_music.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
        assertEquals("Expect correct length of string representation",2846, musicPiece.getMusic().toString().length());


        
    }
    //Covers case of multiple accidentals
    @Test
    public void testAbcSong() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/abc_song.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
        assertEquals("Expect correct length of string representation",255, musicPiece.getMusic().toString().length());

    
        
    }
    //Covers case of being unable to parse
    @Test
    public void TestUnableToParse() {
        MusicLanguage abcLanguage = new MusicLanguage();
        
        boolean error = false;
        String music = "C C DD (";
        try {
            AbcTune musicPiece = abcLanguage.parse(music);
        } catch (UnableToParseException e) {
            error = true;
        }
        assertTrue(error);
        
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

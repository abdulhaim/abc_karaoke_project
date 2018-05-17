package karaoke.sound;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import edu.mit.eecs.parserlib.UnableToParseException;

/**
 * Tests for SoundPlayback
 * @category no_didit
 *
 */

public class SoundPlaybackTest {
    /*
     * Partitions:
     * Listening By Ear:
     *      Chords
     *      Tuplets
     *      Notes of various pitches (flats, sharp, double sharps)
     *      Rest
     *      Chords
     *      Repeats
     *      Multiple Voices
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //Covers partition of playing Note 
    @Test
    public void testPlayNote() throws MidiUnavailableException, InvalidMidiDataException {
        Note note = new Note(new Pitch('C'), 1.0, "");
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        final int beatsPerMinute = 120;
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);

        note.play(player, 0.0, queue);
        player.play();

    }
    
    //Covers partition of playing Chord 

    @Test
    public void testPlayChord() throws MidiUnavailableException, InvalidMidiDataException { 
        List<Note> chords = new ArrayList<Note>();
        chords.add(new Note(new Pitch ('C'), 1.0, ""));
        chords.add(new Note(new Pitch ('D'), 1.0, ""));
        Chord chord = new Chord(chords);
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        final int beatsPerMinute = 120;
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);

        chord.play(player, 0.0, queue);
        player.play();

    }
    
    //Covers partition of playing Tuplet (Triplet) 
    @Test
    public void testPlayTriplet() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch ('C'), 1.0, ""));
        notes.add(new Note(new Pitch ('D'), 1.0, ""));
        notes.add(new Note(new Pitch ('G'), 1.0, ""));
        Tuplet tuplet = new Tuplet(notes,3.0);
        
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        final int beatsPerMinute = 120;
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);

        tuplet.play(player, 0.0, queue);
        player.play();

    }
    
    //Covers partition of playing Tuplet (Duplet) 
    @Test
    public void testPlayDuplet() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'), 1.0, ""));
        notes.add(new Note(new Pitch('D'), 1.0, ""));
        Tuplet duplet = new Tuplet(notes,2.0);

        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        final int beatsPerMinute = 120;
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);

        duplet.play(player, 0.0, queue);
        player.play();

    }
    
    //Covers partition of playing Tuplet (Quadruplet) 
    @Test 
    public void testPlayQuadruplet() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'),1.0, ""));
        notes.add(new Note(new Pitch('D'),1.0, ""));
        notes.add(new Note(new Pitch('D'), 1.0, ""));
        notes.add(new Note(new Pitch('G'),1.0, ""));
        Tuplet quad = new Tuplet(notes,4.0);
        
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        final int beatsPerMinute = 120;
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);

        quad.play(player, 0.0, queue);
        player.play();

    }
    
    //Covers partition of playing Rest
    @Test 
    public void testRest() throws MidiUnavailableException, InvalidMidiDataException {
        Rest rest = new Rest(1.0);
        
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        final int beatsPerMinute = 120;
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);

        rest.play(player, 0.0, queue);
        player.play();

    }
    
    //Covers partition of playing a Bar with Notes
    @Test
    public void testBar() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'),1.0, ""));
        notes.add(new Note(new Pitch('D'),1.0, ""));
        notes.add(new Note(new Pitch('D'), 1.0, ""));
        notes.add(new Note(new Pitch('G'),1.0, ""));
        Bar bar = new Bar(notes);

        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        final int beatsPerMinute = 120;
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);

        bar.play(player, 0.0, queue);
        player.play();

    }
    
    //Covers partition of displaying lyrics with one Voice
    @Test
    public void testSimpleLyricsOneVoice() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/piece3.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
        final int beatsPerMinute = Integer.parseInt(musicPiece.getTempo()); 
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        Voices voice = musicPiece.getMusic();
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        voice.play(player, 0.0, queue);
        player.play();


    }
    //Covers partition of including Note, Chord 
    @Test
    public void testSimpleMusic() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/piece1.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
    
        final int beatsPerMinute = Integer.parseInt(musicPiece.getTempo()); 
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        Voices voice = musicPiece.getMusic();
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        voice.play(player, 0.0, queue);
        player.play();

    }
    
    //Covers partition of having multiple voices
    @Test
    public void testMultipleVoices() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/despacito.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
    
        final int beatsPerMinute = Integer.parseInt(musicPiece.getTempo()); 
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        Voices voice = musicPiece.getMusic();
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        voice.play(player, 0.0, queue);
        player.play();

        
    }
    
    //Covers the partition of having multiple accidentals
    @Test
    public void testComplicatedChordAccidental() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/little_night_music.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
    
        final int beatsPerMinute = Integer.parseInt(musicPiece.getTempo()); 
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        Voices voice = musicPiece.getMusic();
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        voice.play(player, 0.0, queue);
        player.play();

        
    }
    
    //Covers the case of having key accidentals 
    @Test
    public void testAbcSong() throws UnableToParseException, MidiUnavailableException, InvalidMidiDataException {
        MusicLanguage abcLanguage = new MusicLanguage();

        String music = readFile("sample-abc/abc_song.abc");
        final AbcTune musicPiece = abcLanguage.parse(music);
    
        final int beatsPerMinute = Integer.parseInt(musicPiece.getTempo()); 
        final int ticksPerBeat = 12; 
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        Voices voice = musicPiece.getMusic();
        Map<String,BlockingQueue<String>> queue = new HashMap<String,BlockingQueue<String>>();
        voice.play(player, 0.0, queue);
        player.play();

        
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

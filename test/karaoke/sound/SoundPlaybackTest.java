package karaoke.sound;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;
/**
 * Tests for SoundPlayback
 * @author Myra
 *
 */
public class SoundPlaybackTest {
    /*
     * Partitions:
     * Tests by ear: 
     *  test each type of music, and test more complicated music pieces that are created by parsing the example files 
     * Automated Tests:
     *  For each type of music test the functionality of their getDuration and toString functions
     * 
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //The following tests will have to be checked by ear
    @Test
    public void testPlayNote() throws MidiUnavailableException, InvalidMidiDataException {
        SoundPlayback.play(new Note(new Pitch('C'), 1.0));   
    }
    
    @Test
    public void testPlayChord() throws MidiUnavailableException, InvalidMidiDataException {
        List<Note> chords = new ArrayList<Note>();
        chords.add(new Note(new Pitch ('C'), 1.0));
        chords.add(new Note(new Pitch ('D'), 1.0));
        SoundPlayback.play(new Chord(chords));
    }
    
    @Test
    public void testPlayTuplet() throws MidiUnavailableException, InvalidMidiDataException {
        List<Note> notes = new ArrayList<Note>();
        notes.add(new Note(new Pitch ('C'), 1.0));
        notes.add(new Note(new Pitch ('D'), 1.0));
        notes.add(new Note(new Pitch ('G'), 1.0));
        SoundPlayback.play(new Tuplet(notes,3.0));
    }
}

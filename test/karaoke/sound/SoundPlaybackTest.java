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
    public void testPlayTriplet() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch ('C'), 1.0));
        notes.add(new Note(new Pitch ('D'), 1.0));
        notes.add(new Note(new Pitch ('G'), 1.0));
        SoundPlayback.play(new Tuplet(notes,3.0));
    }
    
    @Test
    public void testPlayDuplet() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'), 1.0));
        notes.add(new Note(new Pitch('D'), 1.0));
        Tuplet duplet = new Tuplet(notes,2.0);
        SoundPlayback.play(duplet);
    }
    
    @Test 
    public void testPlayQuadruplet() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'),1.0));
        notes.add(new Note(new Pitch('D'),1.0));
        notes.add(new Note(new Pitch('D'), 1.0));
        notes.add(new Note(new Pitch('G'),1.0));
        Tuplet quad = new Tuplet(notes,4.0);
        SoundPlayback.play(quad);
    }
    
    @Test 
    public void testRest() throws MidiUnavailableException, InvalidMidiDataException {
        Rest rest = new Rest(1.0);
        SoundPlayback.play(rest);
    }
    
    @Test
    public void testBar() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'),1.0));
        notes.add(new Note(new Pitch('D'),1.0));
        notes.add(new Note(new Pitch('D'), 1.0));
        notes.add(new Note(new Pitch('G'),1.0));
        Bar bar = new Bar(notes);
        SoundPlayback.play(bar);
    }
    
    @Test
    public void testRepeatSameEnding() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'),1.0));
        notes.add(new Note(new Pitch('D'),1.0));
        notes.add(new Note(new Pitch('D'), 1.0));
        notes.add(new Note(new Pitch('G'),1.0));
        List<List<Music>> repeatList = new ArrayList<List<Music>>();
        repeatList.add(notes);
        //Repeat rep = new Repeat(repeatList, false);
        //SoundPlayback.play(rep);
    }
    
    @Test
    public void testRepeatDifferentEnding() throws MidiUnavailableException, InvalidMidiDataException {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'),1.0));
        notes.add(new Note(new Pitch('D'),1.0));
        notes.add(new Note(new Pitch('D'), 1.0));
        notes.add(new Note(new Pitch('G'),1.0));
        List<List<Music>> repeatList = new ArrayList<List<Music>>();
        repeatList.add(notes);
        List<Music> ending1 = new ArrayList<Music>();
        ending1.add(new Note(new Pitch('A'), 1.0));
        List<Music> ending2 = new ArrayList<Music>();
        ending2.add(new Note(new Pitch('F'), 1.0));
        repeatList.add(ending1);
        repeatList.add(ending2);
        //Repeat rep = new Repeat(repeatList, true);
        //SoundPlayback.play(rep);
    }
    

    
    
}

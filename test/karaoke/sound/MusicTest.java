package karaoke.sound;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
/**
 * Test functionalities of each concrete variant of Music 
 * 
 * @author Myra
 *
 */
public class MusicTest {
    @Test
    public void testNote() {
        Note note = new Note(1, new Pitch('C'), Instrument.PIANO);
        assertEquals( note.getPitch(), new Pitch('C'));
        assertEquals(note.getDuration(), 1, 0.001);    
    }
    
    @Test
    public void testDuplet() {
        List<Note> notes = new ArrayList<Note>();
        notes.add(new Note(1, new Pitch('C'), Instrument.PIANO));
        notes.add(new Note(1, new Pitch('D'), Instrument.PIANO));
        Tuplet duplet = new Tuplet(notes);
        assertEquals(duplet.getDuration(), 3, 0.001);
        String ans = "(" + duplet.getDuration();
        for (Note note:notes) {
            ans+= note.getPitch();
        }
        assertEquals(duplet.toString(), ans);
    }
    
    @Test
    public void testTriplet() {
        List<Note> notes = new ArrayList<Note>();
        notes.add(new Note(1, new Pitch('C'), Instrument.PIANO));
        notes.add(new Note(1, new Pitch('D'), Instrument.PIANO));
        notes.add(new Note(1, new Pitch('D'), Instrument.PIANO));
        Tuplet triplet = new Tuplet(notes);
        assertEquals(triplet.getDuration(), 2, 0.001);
        String ans = "(" + triplet.getDuration();
        for (Note note:notes) {
            ans+= note.getPitch();
        }
        assertEquals(triplet.toString(), ans);
    }
    
    @Test
    public void testQuadruplet() {
        List<Note> notes = new ArrayList<Note>();
        notes.add(new Note(1, new Pitch('C'), Instrument.PIANO));
        notes.add(new Note(1, new Pitch('D'), Instrument.PIANO));
        notes.add(new Note(1, new Pitch('D'), Instrument.PIANO));
        notes.add(new Note(1, new Pitch('G'), Instrument.PIANO));
        Tuplet quad = new Tuplet(notes);
        assertEquals(quad.getDuration(), 3, 0.001);
        String ans = "(" + quad.getDuration();
        for (Note note:notes) {
            ans+= note.getPitch();
        }
        assertEquals(quad.toString(), ans);
    }
    
    @Test 
    public void testChord() {
        List<Note> chords = new ArrayList<Note>();
        chords.add(new Note(1, new Pitch ('C'), Instrument.PIANO));
        chords.add(new Note(1, new Pitch ('D'), Instrument.PIANO));
        Chord chord = new Chord(chords);
        assertEquals(chord.getDuration(), 1, 0.0001);
        assertEquals(chord.toString(), "[CD]");
    }
    

}

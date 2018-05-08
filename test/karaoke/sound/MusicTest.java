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
        Note note = new Note(new Pitch('C'), 1.0);
        assertEquals( note.getPitch(), new Pitch('C'));
        assertEquals(note.getDuration(), 1, 0.001);    
    }
    
    @Test
    public void testDuplet() {
        List<Note> notes = new ArrayList<Note>();
        notes.add(new Note(new Pitch('C'), 1.0));
        notes.add(new Note(new Pitch('D'), 1.0));
        Tuplet duplet = new Tuplet(notes,2.0);
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
        notes.add(new Note(new Pitch('C'), 1.0));
        notes.add(new Note(new Pitch('D'),1.0));
        notes.add(new Note(new Pitch('D'), 1.0));
        Tuplet triplet = new Tuplet(notes,3.0);
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
        notes.add(new Note(new Pitch('C'),1.0));
        notes.add(new Note(new Pitch('D'),1.0));
        notes.add(new Note(new Pitch('D'), 1.0));
        notes.add(new Note(new Pitch('G'),1.0));
        Tuplet quad = new Tuplet(notes,4.0);
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
        chords.add(new Note(new Pitch ('C'),1.0));
        chords.add(new Note(new Pitch ('D'),1.0));
        Chord chord = new Chord(chords);
        assertEquals(chord.getDuration(), 1, 0.0001);
        assertEquals(chord.toString(), "[CD]");
    }
    

}

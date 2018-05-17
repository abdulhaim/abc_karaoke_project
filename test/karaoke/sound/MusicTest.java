package karaoke.sound;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
/**
 * Test functionalities of each concrete variant of Music 
 *
 * 
 */
public class MusicTest {
    /*
     * Music Interface Partitions:
     *      toString():
     *          Note ADT
     *          Rest ADT
     *          Chord ADT
     *          Tuplet ADT
     *          Bar ADT 
     *          Concat ADT
     *          Voices ADT
     *          Repeat ADT
     *      getDuration():
     *          Note ADT
     *          Rest ADT
     *          Chord ADT
     *          Tuplet ADT
     *          Bar ADT 
     *          Concat ADT
     *          Voices ADT
     *          Repeat ADT
     *      
     *      
     */
    
    //Covers the case of getting duration of a Note
    @Test
    public void testNote() {
        Note note = new Note(new Pitch('C'), 1.0,"OneVoice");
        assertEquals(note.getPitch(), new Pitch('C'));
        assertEquals("Expected correct duration of Note",note.getDuration(), 1, 0.001);    
    }
    
    //Covers the case of getting duration of a Duplet
    @Test
    public void testDuplet() {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'), 1.0,"OneVoice"));
        notes.add(new Note(new Pitch('D'), 1.0,"OneVoice"));
        Tuplet duplet = new Tuplet(notes,2.0);
        assertEquals("Expected correct duration of Duplet",duplet.getDuration(), 2.0, 0.001);
        assertEquals("Expect correct toString", "(2.0C1.0D1.0",duplet.toString());
    }
    
    //Covers the case of a getting toString() of a Triplet
    //Covers the case of a getting duration of a Triplet

    @Test
    public void testTriplet() {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'), 1.0,"OneVoice"));
        notes.add(new Note(new Pitch('D'),1.0,"OneVoice"));
        notes.add(new Note(new Pitch('D'), 1.0,"OneVoice"));
        Tuplet triplet = new Tuplet(notes,3.0);
        assertEquals("Expected correct duration of Triplet",triplet.getDuration(), 3.0, 0.001);
        assertEquals("Expected correct string to Triplet","(3.0C1.0D1.0D1.0",triplet.toString());
    }
    
    //Covers the case of a getting toString() of a Quadruplet
    //Covers the case of a getting duration of a Quadruplet
    
    @Test
    public void testQuadruplet() {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'),1.0,"OneVoice"));
        notes.add(new Note(new Pitch('D'),1.0,"OneVoice"));
        notes.add(new Note(new Pitch('D'), 1.0,"OneVoice"));
        notes.add(new Note(new Pitch('G'),1.0,"OneVoice"));
        Tuplet quad = new Tuplet(notes,4.0);
        assertEquals("Expected correct duration of Tuplet",quad.getDuration(), 4.0, 0.001);
        assertEquals("Expected correct Quadruplet","(4.0C1.0D1.0D1.0G1.0",quad.toString());
    }
    
    //Covers the case of a getting duration of a Chord
    //Covers the case of getting toString of a Chord
    @Test 
    public void testChord() {
        List<Note> chords = new ArrayList<Note>();
        chords.add(new Note(new Pitch ('C'),1.0,"OneVoice"));
        chords.add(new Note(new Pitch ('D'),1.0,"OneVoice"));
        Chord chord = new Chord(chords);
        assertEquals("Expected correct duration of Chord",chord.getDuration(), 1, 0.0001);
        assertEquals("Expected correct string of Chord",chord.toString(), "[C1.0D1.0]");
    }
    
    //Covers the case of getting toString of a rest
    @Test
    public void testRest() {
        Rest rest = new Rest(1.0);
        assertEquals(rest.getDuration(), 1, 0.0001);
        String ans = "." + rest.getDuration();
        assertEquals("Expected correct string of Rest",rest.toString(), ans);
    }
    
    //Covers the case of getting toString of a Bar
    @Test
    public void testBar() {
        List<Music> notes = new ArrayList<Music>();
        notes.add(new Note(new Pitch('C'),1.0,"OneVoice"));
        notes.add(new Note(new Pitch('D'),1.0,"OneVoice"));
        notes.add(new Note(new Pitch('D'), 1.0,"OneVoice"));
        notes.add(new Note(new Pitch('G'),1.0,"OneVoice"));
        Bar bar = new Bar(notes);
        assertEquals(4, bar.getDuration(), 0.001);
        String ans = "C1.0 D1.0 D1.0 G1.0 |";
        assertEquals("Expected correct string of Bar", ans, bar.toString());
        
    }
    

}

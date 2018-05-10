package karaoke.sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An immutable data type that represents a chord 
 * Chord represents a multiple notes played at the same time
 * @author Myra
 *
 */
public class Chord implements Music {
    private final List<Note> notes;
    
    /*
     * AF(notes) = Chord whose notes are the notes in {@param notes}, the duration is set using the duration of the first note 
     * 
     * RI: notes.size >= 2
     * 
     * Safety from Rep: Only field notes is private and final. Notes is also made immutable by wrapping it around
     *                  Collections.unmodifiable wrapper. So the class is immutable. There is no beneficient mutation too.
     *                  
     * Thread-Safety Argument: Only field notes is wrapped around thread-safe wrapper Collections.synchronizedList.
     *                         Also, Note, the argument type of notes is thread-safe.
     * 
     */
    
    /**
     * Create a Chord made up of Notes that lasts for duration 
     * @param notes list of notes
     */
    public Chord (List<Note> notes) {
        this.notes  = Collections.synchronizedList(Collections.unmodifiableList(new ArrayList<Note>(notes)));
        checkRep();
    }
    
    private void checkRep() {
        assert notes != null;
        assert notes.size() >= 2;
    }
   
    @Override
    public double getDuration() {
        return notes.get(0).getDuration();
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        for (Note note : notes) {
            note.play(player, atBeat);
        }
        
    }
    
    @Override
    public int hashCode() {
        long durationBits = Double.doubleToLongBits(this.getDuration());
        return (int) (durationBits ^ (durationBits >>> Integer.SIZE))
                + notes.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Chord other = (Chord) obj;
        return this.getDuration() == other.getDuration()
                && notes.equals(other.notes);
    }
    
    @Override
    public String toString() {
        String ans = "";
        ans += "[";
        for (Note note: notes) {
            ans += note.toString();
        }
        ans += "]";
        return ans;     
    }

    public List<Note> getNotes() {
        return this.notes;
    }

}

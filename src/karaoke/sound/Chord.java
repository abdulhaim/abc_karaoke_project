package karaoke.sound;

import java.util.List;
/*
 * AF(notes, duration) = Chord where notes is a list of notes and the duration is the duration of the chord
 * RI: true
 * Safety from Rep: notes and duration are private and final and the data type is immutable
 */
/**
 * An immutable data type that represents a chord 
 * Chord represents a multiple notes played at the same time
 * @author Myra
 *
 */
public class Chord implements Music {
    private final double duration;
    private final List<Note> notes;
    
    /**
     * Create a Chord made up of Notes that lasts for duration 
     * @param notes list of notes
     * @param duration duration length of chord, equal to the duration of a single note
     */
    public Chord (List<Note> notes, double duration) {
        this.notes  = notes;
        this.duration = duration;  
    }
    
    //TODO checkRep, Chord should not contain rests or tuplets
    @Override
    public double getDuration() {
        // TODO Auto-generated method stub
        return notes.get(0).getDuration();
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public String toString() {
        String ans = "";
        ans += "[";
        for (Note note: notes) {
            ans += note;
        }
        ans += "]";
        return ans;     
    }

}

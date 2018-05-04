package karaoke.sound;

import java.util.List;

/**
 * An immutable data type that represents a chord 
 * Chord represents a multiple notes played at the same time
 * @author Myra
 *
 */
public class Chord implements Music {
    private final double duration;
    private final List<Note> chords;
    
    /**
     * Create a Chord made up of Notes that lasts for duration 
     * @param chords
     * @param duration
     */
    public Chord (List<Note> chords, double duration) {
        this.chords  = chords;
        this.duration = duration;  
    }
    
    //TODO checkRep, Chord should not contain rests or tuplets
    @Override
    public double getDuration() {
        // TODO Auto-generated method stub
        return chords.get(0).getDuration();
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public String toString() {
        String ans = "";
        ans += "[";
        for (Note note: chords) {
            ans += note;
        }
        ans += "]";
        return ans;     
    }

}

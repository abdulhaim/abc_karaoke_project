package karaoke.sound;

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
     * AF(notes) = Chord where notes is a list of notes, the duration is  set using the duration of the first note 
     * RI: true
     * Safety from Rep: notes and duration are private and final and the data type is immutable
     */
    
    /**
     * Create a Chord made up of Notes that lasts for duration 
     * @param notes list of notes
     */
    public Chord (List<Note> notes) {
        this.notes  = notes;
    }
    
    //TODO checkRep, Chord should not contain rests or tuplets, resolve List<Note> or List<Music> and duration
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
    public String toString() {
        String ans = "";
        ans += "[";
        for (Note note: notes) {
            ans += note.getPitch();
        }
        ans += "]";
        return ans;     
    }

}

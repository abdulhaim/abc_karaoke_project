package karaoke.sound;

import java.util.List;

/**
 * enum to represent different types of tuplets.
 * 
 * @author Bibek Kumar Pandit
 */
enum TupletType{
    
    DUPLET (2, 3.0),
    TRIPLET (3, 2.0),
    QUADRUPLET (4, 3.0);
    
    private final int numberOfNotes;
    private final double totalDuration;
    
    /**
     * Constructor of enum TupletType
     * @param numberOfNotes number of notes associated witht Tuplet
     * @param totalDuration duration for which the tuplet plays.
     */
    TupletType(int numberOfNotes, double totalDuration){
        this.numberOfNotes = numberOfNotes;
        this.totalDuration = totalDuration;
    }
    
    public int getNumOfNotes() {return numberOfNotes;}
    public double totalDuration() {return totalDuration;}
}


/**
 * An immutable data type representing a tuplet 
 * 
 * A Tuplet is a consecutive group of notes that are to be played for a duration
 * that is either greater or less than the sum of the individual notes within that group
 * 
 * @author Myra
 *
 */
public class Tuplet implements Music {
    
    
    private final List<Music> notes;
    //private final TupletType tupletType;
    private final double duration;
    
    /*
     * AF(notes, tupletType, duration): 
     *  -  Tuplet where notes is the list of notes that forms the tuplet, tupletType tells the type of tuplet and 
     *     duration is the enlarged/shrunken duration for the entire tuplet
     * 
     * specific type of tuplet (triplet, duplet, quadruplet)
     * Rep Invariant:
     * - notes.size() == tupletType.getNumOfNotes()
     * - duration == tupletType.getTotalDuration()
     * 
     * Safety from Rep: All fields are private and final. tupletType is enum and duration is primitive type, so
     *                  they are immutable. notes is made immutable by wrapping it around the immutable wrapper
     *                  Collections.unmodifiable(). This ADT cannot be mutated, not even beneficient mutation.
     *                             
     */

    /**
     * Creates a Tuplet consisting of notes fitted to the proper duration
     * @param modifiedDuration
     * @param duration
     */
    public Tuplet(List<Music> modifiedDuration, double duration) {
        this.notes = modifiedDuration;
        this.duration = duration;
    }
    
    // TODO checkRep A tuplet may not contain rests, but it may not contain chords.

    @Override
    public double getDuration() {
        return this.duration;
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        // TODO Auto-generated method stub
        
    }
    
    @Override 
    public String toString() {
        String ans = "(";
        ans += this.duration;
        for (Music note: notes) {
            ans += note.toString();
        }
        return ans;   
    }

    public List<Music> getMusic() {
        return this.notes;
    }
    

}
